package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.utils.SessionUtils;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService,
                          UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping(value = "/list")
    public String viewPostList(@RequestParam(value = "page", required = false) Integer page,
                                    HttpServletRequest request,
                                    Model model) {
        HttpSession session = request.getSession(false);
        String searchKeyword = null;
        if(Objects.nonNull(session)){
            searchKeyword = (String) session.getAttribute("searchKeyword");
        }
        List<PostListDTO> pagePostList = postService.getPagePostList(searchKeyword, page, 20);
        int lastPage = postService.getLastPageSize(20, searchKeyword);

        model.addAttribute("lastPage", lastPage);
        model.addAttribute("posts", pagePostList);

        if (SessionUtils.checkLogin(session)) {
            int writerNo = (int) session.getAttribute("no");
            userService.getUserByNo(writerNo).ifPresent(s -> {
                    model.addAttribute("userTypeCode", s.getUserTypeCode());
                }
            );
        }else{
            model.addAttribute("userTypeCode", null);
        }
        return "post/postList";
    }

    @PostMapping("/list/search")
    public String doSearchByTitle(@RequestParam("searchKeyword") String searchKeyword,
                                  HttpServletResponse response,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("searchKeyword", searchKeyword);
        return "redirect:/post/list";
    }

    @GetMapping("/list/search/all")
    public String searchAllList(HttpServletRequest request){
        SessionUtils.deleteSearchKeyword(request.getSession(false));
        return "redirect:/post/list";
    }

    @GetMapping("/list/good")
    public String viewGoodPostList(@RequestParam(value = "page",required = false) Integer page,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model){
        List<PostListDTO> goodPostList = null;
        int lastPage = 1;
        HttpSession session = request.getSession(false);
        if(SessionUtils.checkLogin(session)){
            int loginUserNo = (int) session.getAttribute("no");
            goodPostList = postService.getGoodPostList(loginUserNo, page, 20);
            lastPage = postService.getGoodPostsLastPageSize(loginUserNo,20); //fixme
        }

        model.addAttribute("posts",goodPostList);
        model.addAttribute("lastPage", lastPage);

        return "/post/goodPostList";
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
        if(SessionUtils.checkLogin(session)){
            int writerNo = (int) session.getAttribute("no");
            postService.registerPost(writerNo, title, content);
        }
        return "redirect:/post/list";
    }
}
