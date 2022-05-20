package com.nhnacademy.jdbc.board.index.web;


import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent() && Optional.ofNullable(session.getAttribute("no")).isPresent()){
            mv.addObject("user",userService.getUserByNo((int) session.getAttribute("no")));
        }
        mv.setViewName("/index/index");
        return mv;
    }

    @GetMapping("logout")
    public ModelAndView index(@RequestParam("login") boolean login,
                              HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent() && Optional.ofNullable(session.getAttribute("no")).isPresent() && !login){
            session.removeAttribute("no");
        }
        mv.setViewName("/index/index");
        return mv;
    }
}
