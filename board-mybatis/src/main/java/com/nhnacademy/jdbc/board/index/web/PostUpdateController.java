package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class PostUpdateController {
    private final PostService postService;

    public PostUpdateController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/update")
    public ModelAndView updatePostForm(@RequestParam("postNo") int postNo,
                                       HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");
        Post post = postService.getPost(postNo).get();
        ModelAndView mv = new ModelAndView();
        if (userNo != post.getUserNo()) {
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        }else{
        mv.addObject("post",post);
        mv.setViewName("post/postUpdateForm");
        }
        return mv;
    }

    @PostMapping("/post/update")
    public ModelAndView doUpdatePost(@RequestParam("no") int postNo,
                                     @RequestParam("postTitle") String postTitle,
                                     @RequestParam("postContent") String postContent,
                                     HttpServletRequest request){
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");
        Post post = postService.getPost(postNo).get();
        ModelAndView mv = new ModelAndView();
        if (userNo != post.getUserNo()) {
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        }else{
            post.setPostTitle(postTitle);
            post.setPostContent(postContent);
            post.setModifierUserNo(userNo);
            post.setModifyDatetime(new Timestamp(new Date().getTime()));
            postService.updatePost(post);
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        }
        return mv;
    }
}
