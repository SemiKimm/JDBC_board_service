package com.nhnacademy.jdbc.board.utils;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    private CookieUtils(){}

    public static boolean deleteKeywordCookie(HttpServletRequest request, HttpServletResponse response ){
        Cookie[] cookies = request.getCookies();
        Cookie searchCookie = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals("KEYWORD"))
            .findFirst().orElse(null);

        if(Objects.nonNull(searchCookie)
            && Objects.nonNull(searchCookie.getValue())){
            Cookie deleteCookie = new Cookie("KEYWORD", null);
            deleteCookie.setMaxAge(0);
            deleteCookie.setPath("/");
            response.addCookie(deleteCookie);
            return true;
        }
        return false;
    }
}
