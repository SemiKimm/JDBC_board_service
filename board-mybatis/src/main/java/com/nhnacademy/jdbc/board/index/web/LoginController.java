package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private final UserService userService;

    public LoginController(DefaultUserService defaultUserService) {
        this.userService = defaultUserService;
    }

    @GetMapping
    public String loginForm() {
        return "login/loginForm";
    }


    @PostMapping
    public String doLogin(@Param("id") String id,
                          @Param("password") String password,
                          HttpServletRequest request) {
        Optional<User> user = userService.getUser(id, password);
        if (user.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("id", id);
            log.debug("student : {}", user.get());
            return "redirect:/board/boardList";
        }
        return "login/loginForm";
    }

}
