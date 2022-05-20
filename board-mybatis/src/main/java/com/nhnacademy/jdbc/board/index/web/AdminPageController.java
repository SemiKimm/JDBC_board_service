package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.post.service.impl.DefaultPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("post/deletedPost")
public class AdminPageController {

    private final PostService postService;

    public AdminPageController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView viewDeletedPosts(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post/deletedPostList");
        mv.addObject("deletedPostList",postService.getDeletedPosts());
//        mv.addObject("userTypeValue",userService.getUserByNo())
        return mv;
    }

    @GetMapping("/restorePost")
    public ModelAndView restorePost(@RequestParam("deletedPostNo")int deletedPostNo){
        ModelAndView mv = new ModelAndView();
        postService.restorePost(deletedPostNo);
        mv.setViewName("redirect:/post/deletedPost");
        mv.addObject("deletedPostList",postService.getDeletedPosts());
        return mv;
    }
    @GetMapping("/viewDeletedPost")
    public ModelAndView view(@RequestParam("deletedPostNo")int deletedPostNo){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post/deletedPostView");
        mv.addObject("post",postService.getDeletedPost(deletedPostNo).get());
        return mv;
    }

}