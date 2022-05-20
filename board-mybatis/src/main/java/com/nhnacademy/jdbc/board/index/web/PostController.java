package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private final UserService userService;

    public PostController(PostService postService,
                          UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public ModelAndView viewPostListFirstPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        int lastPage = postService.getLastPageSize();
        List<Post> firstPagePostList = postService.getFirstPagePosts();
        mv.addObject("lastPage", lastPage);
        mv.addObject("posts", firstPagePostList);
        mv.setViewName("post/postList");
        HttpSession session = request.getSession(false);
        Integer userTypeCode = null;
        if(Optional.ofNullable(session).isPresent() && Optional.ofNullable(session.getAttribute("no")).isPresent()){
            int writerNo = (int) session.getAttribute("no");
            if(userService.getUserByNo(writerNo).isPresent()){
                userTypeCode = userService.getUserByNo(writerNo).get().getUserTypeCode();
            }
        }
        mv.addObject("userTypeCode",userTypeCode);
        return mv;
    }

    @GetMapping(value= "/list", params = "page")
    public ModelAndView viewPostListPages(@RequestParam("page") int page,
                                          HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        int lastPage = postService.getLastPageSize();
        List<Post> pagePostList = postService.getPagePosts(page);
        mv.addObject("lastPage", lastPage);
        mv.addObject("posts", pagePostList);
        mv.setViewName("post/postList");
        HttpSession session = request.getSession(false);
        if(Optional.ofNullable(session).isPresent() && Optional.ofNullable(session.getAttribute("no")).isPresent()){
            int writerNo = (int) session.getAttribute("no");
            userService.getUserByNo(writerNo).ifPresent(s->{
                mv.addObject("userTypeCode",s.getUserTypeCode());}
            );
        }
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
