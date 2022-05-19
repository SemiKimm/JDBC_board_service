package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/post")
public class PostController {
    //    @GetMapping
//    public String
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public ModelAndView viewPostListFirstPage() {
        ModelAndView mv = new ModelAndView();
        int lastPage = postService.getLastPageSize();
        List<Post> firstPagePostList = postService.getFirstPagePosts();
        mv.addObject("lastPage", lastPage);
        mv.addObject("posts", firstPagePostList);
        mv.setViewName("post/postList");
        return mv;
    }

    @GetMapping(value= "/list", params = "page")
    public ModelAndView viewPostListPages(@RequestParam("page") int page) {
        ModelAndView mv = new ModelAndView();
        int lastPage = postService.getLastPageSize();
        List<Post> pagePostList = postService.getPagePosts(page);
        mv.addObject("lastPage", lastPage);
        mv.addObject("posts", pagePostList);
        mv.setViewName("post/postList");
        return mv;
    }

    @GetMapping("/register")
    public String registerPostForm() {
        return "post/postForm";
    }

    @PostMapping("/register")
    public String doRegisterPost(@RequestParam("postTitle") String title,
                                 @RequestParam("postContent") String content,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int writerNo = (int) session.getAttribute("no");
        postService.registerPost(writerNo, title, content);
        return "redirect:/post/list";
    }
}
