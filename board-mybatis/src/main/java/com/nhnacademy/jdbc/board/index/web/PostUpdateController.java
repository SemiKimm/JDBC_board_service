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
import org.springframework.ui.Model;
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
    public String updatePostForm(@RequestParam("postNo") int postNo,
                                 HttpServletRequest request,
                                 Model model) {
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");

        User user = userService.getUserByNo(userNo).get();
        Post post = postService.getPost(postNo).get();

        if (userNo == post.getUserNo() || user.getUserTypeCode() == 1) {
            model.addAttribute("post", post);
            return "post/postUpdateForm";
        } else {
            return "redirect:/post/postView?no=" + postNo;
        }
    }

    @PostMapping("/post/update")
    public String doUpdatePost(@RequestParam("no") int postNo,
                               @RequestParam("postTitle") String postTitle,
                               @RequestParam("postContent") String postContent,
                               HttpServletRequest request,
                               Model model) {
        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");

        User user = userService.getUserByNo(userNo).get();
        Post post = postService.getPost(postNo).get();

        if (userNo == post.getUserNo() || user.getUserTypeCode() == 1) {
            post.setPostTitle(postTitle);
            post.setPostContent(postContent);
            post.setModifierUserNo(userNo);
            post.setModifyDatetime(new Timestamp(new Date().getTime()));
            postService.updatePost(post);
            return "redirect:/post/postView?no=" + postNo;
        } else {
            return "redirect:/post/postView?no=" + postNo;
        }
    }
}
