package wojilu.interceptor;

import com.auth0.jwt.JWTVerifyException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wojilu.annotation.Login;
import wojilu.web.Auth;
import wojilu.web.SiteConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Created by shenyuyang
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException, NoSuchAlgorithmException, JWTVerifyException, InvalidKeyException, SignatureException {

        logger.info("...pre...");

        // 凡是打上 Login 注解的页面，都要检查登录
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Login loginAnnotation = method.getAnnotation(Login.class);

        if (loginAnnotation != null) {

            if (isLogin(request) == false) {
                response.sendRedirect(SiteConfig.loginUrl);
                return false;
            }

        }

        return true;
    }


    // 检查用户是否登录
    private Boolean isLogin(HttpServletRequest request) throws NoSuchAlgorithmException, SignatureException, JWTVerifyException, InvalidKeyException, IOException {
        long userId = Auth.getUserId(request);
        return userId > 0;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("...post...");
    }


}
