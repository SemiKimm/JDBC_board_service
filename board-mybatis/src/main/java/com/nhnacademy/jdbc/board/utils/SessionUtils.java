package com.nhnacademy.jdbc.board.utils;

import java.util.Optional;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    private SessionUtils() {
    }

    public static boolean checkLogin(HttpSession session) { //login 되어있는 session 인지 확인
        return Optional.ofNullable(session).isPresent() &&
            Optional.ofNullable(session.getAttribute("no")).isPresent();
    }
}
