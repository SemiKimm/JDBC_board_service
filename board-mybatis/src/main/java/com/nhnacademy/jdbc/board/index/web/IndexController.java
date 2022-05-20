package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.user.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(HttpServletRequest request,
                        Model model) {
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent() &&
            Optional.ofNullable(session.getAttribute("no")).isPresent()) {
            model.addAttribute("user", userService.getUserByNo((int) session.getAttribute("no")));
        }
        return "/index/index";
    }

    @GetMapping("logout")
    public String index(@RequestParam("login") boolean login,
                        HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent() &&
            Optional.ofNullable(session.getAttribute("no")).isPresent() && !login) {
            session.removeAttribute("no");
        }
        return "/index/index";
    }
}
