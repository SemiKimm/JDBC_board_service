package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostUpdateController {
    private final PostService postService;
    private final UserService userService;

    public PostUpdateController(PostService postService,
                                UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/post/update")
    public ModelAndView updatePostForm(@RequestParam("postNo") int postNo,
                                       HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");

        User user = userService.getUserByNo(userNo).get();
        Post post = postService.getPost(postNo).get();

        ModelAndView mv = new ModelAndView();
        if (userNo == post.getUserNo() || user.getUserTypeCode() == 1) {
            mv.addObject("post", post);
            mv.setViewName("post/postUpdateForm");
        } else {
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        }
        return mv;
    }

    @PostMapping("/post/update")
    public ModelAndView doUpdatePost(@RequestParam("no") int postNo,
                                     @RequestParam("postTitle") String postTitle,
                                     @RequestParam("postContent") String postContent,
                                     HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");

        User user = userService.getUserByNo(userNo).get();
        Post post = postService.getPost(postNo).get();

        ModelAndView mv = new ModelAndView();

        if (userNo == post.getUserNo() || user.getUserTypeCode() == 1) {
            post.setPostTitle(postTitle);
            post.setPostContent(postContent);
            post.setModifierUserNo(userNo);
            post.setModifyDatetime(new Timestamp(new Date().getTime()));
            postService.updatePost(post);
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        } else {
            mv.setViewName("redirect:/post/postView?no=" + postNo);
        }
        return mv;
    }
}
