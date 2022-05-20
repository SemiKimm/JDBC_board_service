package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(
        CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/register")
    public String doRegisterComment(@RequestParam("postNo") int postNo,
                                    @RequestParam("commentContent") String commentContent,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loginUserNo = (int) session.getAttribute("no");
        commentService.addComment(new Comment(commentContent, postNo, loginUserNo));

        return "redirect:/post/postView?no=" + postNo;
    }

    @GetMapping("/modify")
    public String modifyCommentForm(@RequestParam("commentNo") int commentNo,
                                    HttpServletRequest request,
                                    Model model) {
        HttpSession session = request.getSession(false);
        commentService.getComment(commentNo).ifPresent(comment -> {
            if (comment.getUserNo() == (int) session.getAttribute("no")) {
                model.addAttribute("comment", comment);
            }
        });
        return "/comment/commentModifyForm";
    }

    @PostMapping("/modify")
    public String doModifyComment(@RequestParam("commentNo") int commentNo,
                                  @RequestParam("commentContent") String content,
                                  @RequestParam("postNo") int postNo,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loginUserNo = (int) session.getAttribute("no");
        commentService.modifyComment(loginUserNo, commentNo, content);

        return "redirect:/post/postView?no=" + postNo;
    }

    @GetMapping("/delete")
    public String doDeleteComment(@RequestParam("commentNo") int commentNo,
                                  @RequestParam("postNo") int postNo) {
        commentService.deleteComment(commentNo);

        return "redirect:/post/postView?no=" + postNo;
    }
}
