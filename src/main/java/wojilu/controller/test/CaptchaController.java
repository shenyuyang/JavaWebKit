package wojilu.controller.test;

import com.google.code.kaptcha.servlet.KaptchaExtend;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenyuyang
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController extends KaptchaExtend {


    @RequestMapping(value = "/img.jpg", method = RequestMethod.GET)
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.captcha(req, resp);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelAndView form(@RequestParam(value = "error", required = false) boolean failed,
                             HttpServletRequest request) {
        ModelAndView model = new ModelAndView("captcha/form");

        return model;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ModelAndView validate(@RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("captcha/validate");

        if (email.isEmpty())
            throw new RuntimeException("email empty");

        if (password.isEmpty())
            throw new RuntimeException("empty password");

        String captcha = request.getParameter("captcha");

        if (!captcha.equals(getGeneratedKey(request)))
            throw new RuntimeException("bad captcha");


        return model;
    }

}
