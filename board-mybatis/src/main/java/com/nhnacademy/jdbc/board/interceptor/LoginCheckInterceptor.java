package com.nhnacademy.jdbc.board.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override //로그인한 사용자 는 전부 가능 +
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("no") == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
