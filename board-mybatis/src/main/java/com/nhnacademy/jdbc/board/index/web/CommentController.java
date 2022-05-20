package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(
        CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/register")
    public ModelAndView doRegisterComment(@RequestParam("postNo") int postNo,
                                          @RequestParam("commentContent") String commentContent,
                                          HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        int loginUserNo = (int) session.getAttribute("no");
        commentService.addComment(new Comment(commentContent, postNo, loginUserNo));

        mv.setViewName("redirect:/post/postView?no=" + postNo);
        return mv;
    }

    @GetMapping("/modify")
    public ModelAndView modifyCommentForm(@RequestParam("commentNo") int commentNo,
                                          HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);

        commentService.getComment(commentNo).ifPresent(comment -> {
            if (comment.getUserNo() == (int) session.getAttribute("no")) {
                mv.addObject("comment", comment);
                mv.setViewName("/comment/commentModifyForm");
            }
        });

        return mv;
    }

    @PostMapping("/modify")
    public ModelAndView doModifyComment(@RequestParam("commentNo") int commentNo,
                                        @RequestParam("commentContent") String content,
                                        @RequestParam("postNo") int postNo,
                                        HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession(false);
        int loginUserNo = (int) session.getAttribute("no");
        commentService.modifyComment(loginUserNo, commentNo, content);
        mv.setViewName("redirect:/post/postView?no=" + postNo);
        return mv;
    }

    @GetMapping("/delete")
    public ModelAndView doDeleteComment(@RequestParam("commentNo") int commentNo,
                                        @RequestParam("postNo") int postNo) {
        ModelAndView mv = new ModelAndView();
        commentService.deleteComment(commentNo);
        mv.setViewName("redirect:/post/postView?no=" + postNo);
        return mv;
    }
}
