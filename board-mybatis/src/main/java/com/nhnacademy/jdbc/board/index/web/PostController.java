package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {
    //    @GetMapping
//    public String
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String viewPostList(Model model){
        model.addAttribute("posts", postService.getPosts());
        return "board/boardList";
    }

    @GetMapping("/register")
    public String registerPostForm() {
        return "board/boardForm";
    }

    @PostMapping("/register")
    public String doRegisterPost(@RequestParam("postTitle") String title,
                                 @RequestParam("postContent") String content) {
        postService.registerPost(title, content);
        return "redirect:/post/list";
    }
}
