package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

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
    public String viewPage(@RequestParam("no") int no,
                           HttpServletRequest request,
                           Model model){
        HttpSession session = request.getSession(false);
        Integer loginUserNo = null;
        if(Objects.nonNull(session) &&Objects.nonNull(session.getAttribute("no"))){
            loginUserNo = (int) session.getAttribute("no");
        }
        postService.getPost(no).ifPresent(post->{
            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
            model.addAttribute("post",post);
        });
        model.addAttribute("comments",commentService.getComments(no));
        model.addAttribute("loginUserNo",loginUserNo);
        return "post/postView";
    }

    @GetMapping("/likeUpdate")
    public String likeUpdate(@RequestParam("postNo") int no,
                             HttpServletRequest request,
                             Model model){
        HttpSession session = request.getSession(false);
        Integer loginUserNo = null;
        if(Objects.nonNull(session) &&Objects.nonNull(session.getAttribute("no"))){
            loginUserNo = (int) session.getAttribute("no");
        }
        postService.getPost(no).ifPresent(post->{
            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
            model.addAttribute("post",post);
        });
        model.addAttribute("comments",commentService.getComments(no));
        model.addAttribute("loginUserNo",loginUserNo);
        return "post/postView";
    }

}
