package wojilu.web.csrf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wojilu.web.Html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenyuyang
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(CsrfInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) return true;

        // 除GET 之外的所有请求，都要验证
        // 所有非 GET 请求，都必须有 token

        if (Html.isCsrfValid(request)) {
            Html.clearCsrfToken(request); // 清空，防止重复刷新
            return true;
        }
        else {

            // 验证错误1：清空
            Html.clearCsrfToken(request);

            // 验证错误2：反馈错误信息
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("<p>CSRF token error</p>");

            return false;
        }

    }


}