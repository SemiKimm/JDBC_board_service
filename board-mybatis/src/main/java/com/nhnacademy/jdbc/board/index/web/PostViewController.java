package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("post/postView")
public class PostViewController {

    private final PostService postService;
    private final CommentService commentService;
    private final GoodService goodService;
    private final FileService fileService;

    public PostViewController(PostService postService,
                              CommentService commentService,
                              GoodService goodService,
                              FileService fileService) {
        this.postService = postService;
        this.commentService = commentService;
        this.goodService = goodService;
        this.fileService = fileService;
    }

    @GetMapping
    public String viewPage(@RequestParam("no") int no,//fixme : 이 no가 postNo 맞는지 다시 확인하고 변수명 바꾸기
                           HttpServletRequest request,
                           Model model){
        HttpSession session = request.getSession(false);
        Integer loginUserNo = null;
        if(Objects.nonNull(session) &&Objects.nonNull(session.getAttribute("no"))){
            loginUserNo = (int) session.getAttribute("no");
            postService.addViewCount(loginUserNo, no);
        }
        postService.getPost(no).ifPresent(post->{
            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
            model.addAttribute("post",post);
        });
        model.addAttribute("comments",commentService.getComments(no));
        model.addAttribute("loginUserNo",loginUserNo);
        model.addAttribute("goodCount",goodService.getGoodCount(no));
        model.addAttribute("file",fileService.selectFile(no));
        return "post/postView";
    }

//    @GetMapping("/likeUpdate")
//    public String likeUpdate(@RequestParam("postNo") int no,
//                             HttpServletRequest request,
//                             Model model){
//        HttpSession session = request.getSession(false);
//        Integer loginUserNo = null;
//        if(Objects.nonNull(session) &&Objects.nonNull(session.getAttribute("no"))){
//            loginUserNo = (int) session.getAttribute("no");
//        }
//        postService.getPost(no).ifPresent(post->{
//            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
//            model.addAttribute("post",post);
//        });
//        model.addAttribute("comments",commentService.getComments(no));
//        model.addAttribute("loginUserNo",loginUserNo);
//        return "post/postView";
//    }

}
