package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
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
        List<Post> postList = postService.getPosts();
        model.addAttribute("posts", postList);
        return "post/postList";
    }

    @GetMapping("/register")
    public String registerPostForm() {
        return "post/postForm";
    }

    @PostMapping("/register")
    public String doRegisterPost(@RequestParam("postTitle") String title,
                                 @RequestParam("postContent") String content) {
        postService.registerPost(title, content);
        return "redirect:/post/list";
    }
}
