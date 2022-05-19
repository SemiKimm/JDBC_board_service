package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostUpdateController {
    private final PostService postService;

    public PostUpdateController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/update")
    public String updatePostForm(@RequestParam("postNo") int postNo,
                               HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");
        Post post = postService.getPost(postNo).get();
        if (userNo == post.getUserNo()) {
            return "/post/postUpdateForm";
        }
        else{
            return "redirect:/post/postView?no=" + postNo;
        }
    }

    @PostMapping("/post/")
    public String doUpdatePost(){
        return null;
    }
}
