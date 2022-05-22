package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.dto.UserDto;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String doLogin(@ModelAttribute @Validated UserDto userDto,
                          BindingResult bindingResult,
                          HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            log.error("로그인 입력값이 규격에 맞지 않습니다");
            return "login/loginForm";
        }
        Optional<User> user = userService.getUser(userDto.getUserId(), userDto.getUserPassword());
        if (user.isPresent()) {

            HttpSession session = request.getSession(true);
            session.setAttribute("no", user.get().getUserNo());
            return "redirect:/post/list";
        }
        return "login/loginForm";
    }

}
