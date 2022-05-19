package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostDeleteController {
    private final PostService postService;

    public PostDeleteController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String doDeletePost(@RequestParam("postNo") int postNo){
        return "redirect:/post/list";
    }
}
