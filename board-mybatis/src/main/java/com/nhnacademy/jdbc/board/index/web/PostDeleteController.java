package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nhnacademy.jdbc.board.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostDeleteController {
    private final PostService postService;
    private final UserService userService;

    public PostDeleteController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/post/delete")
    public String doDeletePost(@RequestParam("postNo") int postNo,
                               HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");
        Post post = postService.getPost(postNo).get();
        if (userNo == post.getUserNo()) {
            postService.deletePost(postNo);
            return "redirect:/post/list";
        }
        else{
            return "redirect:/post/postView?no=" + postNo;
        }
    }
}
