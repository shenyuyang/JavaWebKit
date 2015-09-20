package wojilu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by shenyuyang
 */
public final class Html {

    public static final String requestTokenName = "__csrf";
    public static final String headerTokenName = "X-CSRF-TOKEN";
    public static final String sessionTokenName = "csrf-server-token";

    private Html() {
    }

    public static String csrfToken(HttpServletRequest request) {
        String tokenValue = createCsrfToken(request);
        return "<input type=\"hidden\" name=\"" + requestTokenName + "\" value=\"" + tokenValue + "\" />";
    }

    public static Boolean isCsrfValid(HttpServletRequest request) {

        if (isAjax(request)) {

            String tokenFromHeader = getTokenFromHeader(request);
            if (tokenFromHeader == null) return false;

            String tokenFromSession = getTokenFromSession(request);
            return tokenFromHeader.equals(tokenFromSession);
        } else {

            String tokenFromRequest = getTokenFromRequest(request);
            if (tokenFromRequest == null) return false;

            String tokenFromSession = getTokenFromSession(request);
            return tokenFromRequest.equals(tokenFromSession);
        }
    }


    public static void clearCsrfToken(HttpServletRequest request) {
        request.getSession().removeAttribute(sessionTokenName);
    }

    public static String createCsrfToken(HttpServletRequest request) {
        String tokenValue = null;

        HttpSession session = request.getSession();

        synchronized (session) {
            tokenValue = (String) session.getAttribute(sessionTokenName);
            if (tokenValue == null) {
                tokenValue = UUID.randomUUID().toString();
                session.setAttribute(sessionTokenName, tokenValue);
            }
        }
        return tokenValue;
    }

    //-------------------------------------------------------------------------

    private static String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(headerTokenName);
    }

    private static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(requestTokenName);
    }

    private static String getTokenFromSession(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(sessionTokenName);
        if (obj == null) return null;
        return obj.toString();
    }

    private static boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }


}
