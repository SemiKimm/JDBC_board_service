package com.nhnacademy.jdbc.board.index.web;


import com.nhnacademy.jdbc.board.good.service.GoodService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/post/postView/isUserGoodToPost")
public class PostGoodController {

    private final GoodService goodService;

    public PostGoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping
    public String isUserGoodToPost(@RequestParam(required=false, name = "postNo") Integer postNo,
                                   HttpServletRequest request,
                                   Model model){
        if (postNo == null){
            throw new RuntimeException();
        }
        HttpSession session = request.getSession(false);
        int userNo =  (int) session.getAttribute("no");
        model.addAttribute("goodCount",goodService.getGoodCount(postNo));
        if (goodService.isUserGoodToPost(postNo,userNo).isPresent()){
            goodService.deleteGood(postNo,userNo);
        }else{
            goodService.insertGood(postNo,userNo);
        }
        return "post/postView?no=" + Optional.of(postNo).get();
    }
}
