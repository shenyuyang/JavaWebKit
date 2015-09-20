package wojilu.web;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyuyang
 */
public class Auth {


    public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] arrCookie = request.getCookies();
        for (Cookie cookie : arrCookie) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }


    public static long getUserId(HttpServletRequest request)
            throws SignatureException, NoSuchAlgorithmException, JWTVerifyException, InvalidKeyException, IOException {

        Cookie cookie = getCookieByName(request, SiteConfig.authCookieName);
        if (cookie == null) return 0;

        JWTVerifier verifier = new JWTVerifier(SiteConfig.authCookieTokenKey);
        String token = cookie.getValue();
        if (!StringUtils.hasText(token)) return 0;

        Map<String, Object> decoded = verifier.verify(token);

        HashMap<String, Object> deUser = (HashMap<String, Object>) decoded.get("user");

        Object strUserId = deUser.get("id");
        if (strUserId == null) return 0;

        return Long.parseLong(strUserId.toString());
    }

    public static void addAuthCookie( HttpServletResponse response,long userId, int rememberDays) {

        Cookie cookie = Auth.makeAuthCookie(userId, rememberDays); // 记住30天
        response.addCookie(cookie);
    }

    private static Cookie makeAuthCookie(long userId, int rememberDays) {
        String token = makeAuthToken(userId);

        int maxAge = 60 * 60 * 24 * rememberDays; // 天
        Cookie cookie = new Cookie(SiteConfig.authCookieName, token);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        return cookie;
    }

    private static String makeAuthToken(long userId) {
        JWTSigner signer = new JWTSigner(SiteConfig.authCookieTokenKey);

        HashMap<String, Object> claims = new HashMap<String, Object>();

        AuthUser user = new AuthUser(userId);
        claims.put("user", user);
        return signer.sign(claims);
    }

    public static void clearAuthCookie(HttpServletResponse response) {

        Cookie cookie = new Cookie(SiteConfig.authCookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

    }


}
