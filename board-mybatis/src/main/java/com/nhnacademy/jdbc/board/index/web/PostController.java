package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.utils.CookieUtils;
import com.nhnacademy.jdbc.board.utils.SessionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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

    @GetMapping("/list")
    public String viewPostListFirstPage() {
        return "redirect:/post/list?page=" + 1;
    }

    @GetMapping(value = "/list", params = "page")
    public String viewPostListPages(@RequestParam("page") int page,
                                    @CookieValue(value = "KEYWORD", required = false) String keywordCookie,
                                    HttpServletRequest request,
                                    Model model) {
        List<PostListDTO> pagePostList = postService.getPagePostList(keywordCookie, page, 20);
        int lastPage = postService.getLastPageSize(20);

        model.addAttribute("lastPage", lastPage);
        model.addAttribute("posts", pagePostList);

        HttpSession session = request.getSession(false);
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
                                  HttpServletResponse response) {
        Cookie searchCookie = new Cookie("KEYWORD", searchKeyword);
        searchCookie.setPath("/");
        response.addCookie(searchCookie);

        return "redirect:/post/list";
    }

    @GetMapping("/list/search/all")
    public String searchAllList(HttpServletRequest request,
                                HttpServletResponse response){
        CookieUtils.deleteKeywordCookie(request, response);
        return "redirect:/post/list";
    }

    @GetMapping("/list/good")
    public String searchGoodPostList(@RequestParam(value = "page",required = false) Integer page,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model){
        List<PostListDTO> goodPostList = null;
        int lastPage = 1;
        HttpSession session = request.getSession(false);
        if(SessionUtils.checkLogin(session)){
            int loginUserNo = (int) session.getAttribute("no");
            goodPostList = postService.getGoodPostList(loginUserNo, page, 20);
            lastPage = postService.getLastPageSize(20);
        }

        model.addAttribute("posts",goodPostList);
        model.addAttribute("lastPage", lastPage);

        return "/post/goodPostList";
    }

    @GetMapping("/register")
    public String registerPostForm() {
        return "post/postForm";
    }

    @PostMapping("/register") // fixme : 여기 충돌날듯
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
