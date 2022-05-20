package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("post/deletedPost")
public class AdminPageController {

    private final PostService postService;

    public AdminPageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String viewDeletedPosts(Model model) {
        model.addAttribute("deletedPostList", postService.getDeletedPosts());
        return "post/deletedPostList";
    }

    @GetMapping("/restorePost")
    public String restorePost(@RequestParam("deletedPostNo") int deletedPostNo,
                              Model model) {
        postService.restorePost(deletedPostNo);
        model.addAttribute("deletedPostList", postService.getDeletedPosts());
        return "redirect:/post/deletedPost";
    }

    @GetMapping("/viewDeletedPost")
    public String view(@RequestParam("deletedPostNo") int deletedPostNo,
                       Model model) {
        model.addAttribute("post", postService.getDeletedPost(deletedPostNo).get());
        return "post/deletedPostView";
    }
}