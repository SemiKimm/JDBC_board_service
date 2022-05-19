package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Controller
@RequestMapping("post/postView")
public class PostViewController {

    private final PostService postService;

    public PostViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView viewPage(@RequestParam("no") int no){
        ModelAndView mv = new ModelAndView();
        postService.getPost(no).ifPresent(post->{
            mv.addObject("post",post);
        });
        mv.setViewName("post/postView");
        return mv;
    }

}
