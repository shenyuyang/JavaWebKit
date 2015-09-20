package wojilu.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wojilu.domain.Article;
import wojilu.service.ArticleService;

/**
 * Created by shenyuyang
 */
@Controller
@RequestMapping(value="home")
public class HomeController {

    private static Log logger = LogFactory.getLog(HomeController.class);

    @RequestMapping(value = "index")
    @ResponseBody
    public String index(ModelMap map) {

        logger.info("home index log");

        return "hello index";
    }

}