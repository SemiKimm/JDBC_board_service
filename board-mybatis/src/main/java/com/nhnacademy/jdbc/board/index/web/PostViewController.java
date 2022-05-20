package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final CommentService commentService;

    public PostViewController(PostService postService,
                              CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public ModelAndView viewPage(@RequestParam("no") int no,
                                 HttpServletRequest request){
        HttpSession session = request.getSession(false);
        int loginUserNo = (int) session.getAttribute("no");
        ModelAndView mv = new ModelAndView();
        postService.getPost(no).ifPresent(post->{
            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
            mv.addObject("post",post);
        });
        mv.addObject("comments",commentService.getComments(no));
        mv.addObject("loginUserNo",loginUserNo);
        mv.setViewName("post/postView");
        return mv;
    }

}
