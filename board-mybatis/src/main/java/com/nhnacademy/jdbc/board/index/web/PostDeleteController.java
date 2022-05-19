package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostDeleteController {
    private final PostService postService;

    public PostDeleteController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/delete")
    public String doDeletePost(@RequestParam("postNo") int postNo,
                               HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        int userNo = (int) session.getAttribute("no");
        postService.getPost(postNo).ifPresent(post -> {
            if (userNo == post.getUserNo()) {
                postService.deletePost(postNo);
            }
            else{
                throw new RuntimeException(); // todo : 삭제하려는 사용자랑 글 작성자가 다른 경우 (관리자일 경우에는 허용해야 하는거 추가해야된다)
            }
        });
        return "redirect:/post/list";
    }
}
