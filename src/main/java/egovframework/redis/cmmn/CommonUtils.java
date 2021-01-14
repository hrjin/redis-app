package egovframework.redis.cmmn;

import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Common Utils 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.13
 */
public class CommonUtils {

    /**
     * Cookies 추가 (HttpOnly = true, 만료 1시간 )
     *
     * @param response
     * @param name the name of cookie
     * @param value the value
     */
    public static void addCookies(HttpServletResponse response, String name, String value) {

        CookieGenerator cookie = new CookieGenerator();
        cookie.setCookieName(name);
        cookie.setCookieMaxAge(60 * 60); // 1hours
        cookie.setCookieHttpOnly(true);
        cookie.addCookie(response, value);
    }


    /**
     * Cookies 삭제
     *
     * @param response
     * @param name the name of cookie
     */
    public static void removeCookies(HttpServletResponse response, String name) {

        CookieGenerator cookie = new CookieGenerator();

        cookie.setCookieName(name);
        cookie.setCookieMaxAge(0);
        cookie.addCookie(response, null);

    }


    /**
     * Cookies 가져오기
     *
     * @param request
     * @param name the name of cookie
     * @return the String
     */
    public static String getCookie(HttpServletRequest request, String name) {

        String cookieValue = null;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                cookieValue = cookies[i].getValue();
            }
        }
        return cookieValue;
    }
}
