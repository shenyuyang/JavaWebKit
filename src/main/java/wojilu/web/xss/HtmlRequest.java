package wojilu.web.xss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by shenyuyang
 */
public class HtmlRequest extends HttpServletRequestWrapper {

    private static final Log logger = LogFactory.getLog(HtmlRequest.class);

    public HtmlRequest(HttpServletRequest servletRequest) {
        super(servletRequest);

        logger.info("-------- init request wrapper --------");
    }

    public String[] getParameterValues(String parameter) {

        logger.info("-------- getParameterValues --------");


        String[] values = super.getParameterValues(parameter);

        if (values == null) return null;

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    /**
     * 获取用户提交的内容，已过滤掉所有标签
     * @param parameter
     * @return
     */
    public String getParameter(String parameter) {

        logger.info("-------- getParameter --------");

        String value = super.getParameter(parameter);
        if (value == null) return null;

        return cleanXSS(value);

    }

    /**
     * 获取用户提交的内容，允许基本的 html 标签，基于 jsoup 的白名单过滤
     * @param parameter
     * @return
     */
    public String getParameterHtml(String parameter) {
        logger.info("-------- getParameterHtml --------");

        String value = super.getParameter(parameter);
        if (value == null) return null;

        return cleanByWhitelist(value);
    }


    /**
     * 获取用户提交的内容，所有 html 标签都允许，非常危险，不推荐使用
     * @param parameter
     * @return
     */
    public String getParameterHtmlAll(String parameter) {

        logger.info("-------- getParameterHtmlAll --------");

        return super.getParameter(parameter);
    }


    public String getHeader(String name) {

        logger.info("-------- getHeader --------");

        String value = super.getHeader(name);
        if (value == null) return null;

        return cleanXSS(value);

    }

    private String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        return value;
    }

    private String cleanByWhitelist(String value) {
        return Jsoup.clean(value, Whitelist.relaxed());
    }



}