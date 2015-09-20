package wojilu.controller.test;

import com.auth0.jwt.JWTVerifyException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import wojilu.annotation.Login;
import wojilu.domain.Article;
import wojilu.domain.User;
import wojilu.service.ArticleService;
import wojilu.web.Auth;
import wojilu.web.xss.HtmlRequest;
import wojilu.mail.MailAuth;
import wojilu.web.Html;
import wojilu.service.UserService;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Properties;

/**
 * Created by shenyuyang
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    private static Log logger = LogFactory.getLog(TestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    //---------------------hibernate-------------------------------------------

    @RequestMapping(value = "index")
    @ResponseBody
    public String index(ModelMap map) {

        logger.info("home index log");

        StringUtils.hasText("");
        StringUtils.isEmpty("");

        Article x = articleService.getById(2);

        return "hello world, "+ articleService.getName() + ",=>article.Title:"+x.getTitle();
    }


    //---------------------login-------------------------------------------


    @Login
    @RequestMapping(value = "needLogin")
    @ResponseBody
    public String needLogin() {
        return "这是需要登录才能看到的页面";
    }


    @RequestMapping("/post")
    public void post(){
        // 自动映射 view
    }

    //---------------------CSRF-------------------------------------------

    @RequestMapping(value = "/xssForm")
    public String xssForm() {
        return "/test/xssForm";
    }

    @RequestMapping(value = "xssCheck", method = RequestMethod.POST)
    @ResponseBody
    public String xssCheck(HttpServletRequest request) {

        logger.info("---------action----------");

        String title = request.getParameter("txtTitle");
        String body = request.getParameter("txtBody");

        String htmlContent = ((HtmlRequest) request).getParameterHtml("htmlContent");

        return "request title=" + title + "<br>" +
                "request body=" + body + "<br>" +
                "request html=" + htmlContent
                ;
    }

    //---------------------CSRF-------------------------------------------

    @RequestMapping(value = "/csrfForm")
    public String csrfForm() {
        return "/test/csrfForm";
    }

    @RequestMapping(value = "csrfCheck", method = RequestMethod.POST)
    @ResponseBody
    public String csrfCheck(HttpServletRequest request) {

        // 不需要检查 anti csrf token，因为 interceptor 会自动拦截检查

        return "ok,request token=" + request.getParameter(Html.requestTokenName);

    }


    //---------------------mail-------------------------------------------

    @RequestMapping(value = "sendMailForm")
    public String sendMailForm() {
        return "/test/sendMailForm";
    }


    @RequestMapping(value = "sendMail")
    public String sendMail(HttpServletRequest request) throws Exception {


        String smtpHost = request.getParameter("smtpHost");
        String senderMail = request.getParameter("senderMail");
        String senderPassword = request.getParameter("senderPassword");

        String mailTitle = request.getParameter("mailTitle");
        String mailBody = request.getParameter("mailBody");
        String receiverMail = request.getParameter("receiverMail");

        // 配置
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.auth", "true");

        // 登录用户名+密码
        MailAuth auth = new MailAuth(senderMail, senderPassword);

        // 创建连接
        Session session = Session.getDefaultInstance(properties, auth);


        // 创建邮件
        MimeMessage msg = new MimeMessage(session);

        // 发信人+收信人
        Address addrFrom = new InternetAddress(senderMail);
        Address addrTo = new InternetAddress(receiverMail);
        msg.setFrom(addrFrom);
        msg.setRecipient(Message.RecipientType.TO, addrTo);

        // 标题+内容
        msg.setSubject(mailTitle);
        msg.setContent(mailBody, "text/plain; charset=utf-8");

        msg.saveChanges();

        // 发送
        Transport transport = session.getTransport("smtp");
        transport.connect(smtpHost, senderMail, senderPassword);
        transport.send(msg);
        transport.close();


        return "/test/sendMail";
    }


    //---------------------用户cookie加密+验证-------------------------------------------


    @RequestMapping(value = "/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) throws NoSuchAlgorithmException, SignatureException, JWTVerifyException, InvalidKeyException, IOException {

        ModelAndView x = new ModelAndView("/test/userInfo");

        long userId = Auth.getUserId(request);
        logger.info("userId=>" + userId);
        User user = userService.getById(userId);

        x.addObject("user", user);

        return x;

    }

    @RequestMapping(value = "userCheckLogin")
    @ResponseBody
    public String userCheckLogin(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");

        if (name == null || name.length() == 0) return "请填写名称";
        if (pwd == null || pwd.length() == 0) return "请填写pwd";

        User x = userService.checkByNameAndPwd(name, pwd);
        if (x == null) {
            return "用户名或密码错误";
        }

        int rememberDays = 30;
        Auth.addAuthCookie(response, x.getId(), rememberDays);

        return "ok";
    }


    @RequestMapping(value = "/userLogout")
    public String userLogout(HttpServletResponse response) {

        Auth.clearAuthCookie(response);

        return "redirect:/";

    }

    //---------------------上传功能-------------------------------------------

    @RequestMapping(value = "/fileForm")
    public String fileForm() {
        return "/test/fileForm";
    }

    @RequestMapping(value = "/saveUpload", method = RequestMethod.POST)
    public ModelAndView saveUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile myFile = multipartRequest.getFile("myFile");

        // 文件保存
        // 1) 上传路径
        String uploadPath = "upload";

        // 2) 检测绝对路径是否存在，否则创建
        String absDirPath = request.getSession().getServletContext().getRealPath("/") + uploadPath + "/";
        File dir = new File(absDirPath);
        if (dir.exists() == false) {
            dir.mkdir();
        }

        // 3) 存储文件
        String filePath = absDirPath + myFile.getOriginalFilename();
        myFile.transferTo(new File(filePath));

        // 缩略图
        Thumbnails.of(filePath)
                .size(150, 120)
                .toFile(filePath + "_s.jpg");

        // 返回相对网址
        ModelAndView x = new ModelAndView("test/saveUpload");
        String fileName = request.getContextPath() + "/" + uploadPath + "/" + myFile.getOriginalFilename();
        x.addObject("msg", fileName);
        return x;
    }

}
