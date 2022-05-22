package com.nhnacademy.jdbc.board.index.web;


import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/post/postView/isUserGoodToPost")
public class PostGoodController {

    private final GoodService goodService;
    private final PostService postService;
    private final CommentService commentService;
    private final FileService fileService;

    public PostGoodController(GoodService goodService,PostService postService,CommentService commentService,FileService fileService) {
        this.goodService = goodService;
        this.postService = postService;
        this.commentService = commentService;
        this.fileService = fileService;
    }

    @GetMapping
    public String isUserGoodToPost(@RequestParam("postNo") int postNo,
                                   HttpServletRequest request,
                                   Model model){
//        if (postNo == null){
//            throw new RuntimeException();
//        }
        HttpSession session = request.getSession(false);
        Integer loginUserNo = null;
        if(Objects.nonNull(session) &&Objects.nonNull(session.getAttribute("no"))){
            loginUserNo = (int) session.getAttribute("no");
        }
        postService.getPost(postNo).ifPresent(post->{
            post.setPostContent(post.getPostContent().replace("\n","<br/>"));
            model.addAttribute("post",post);
        });
        model.addAttribute("comments",commentService.getComments(postNo));
        model.addAttribute("loginUserNo",loginUserNo);
        int userNo =  (int) session.getAttribute("no");
        if (goodService.isUserGoodToPost(postNo,userNo)){
            goodService.deleteGood(postNo,userNo);
        }else{
            goodService.insertGood(postNo,userNo);
        }
        model.addAttribute("goodCount",goodService.getGoodCount(postNo));
        model.addAttribute("file",fileService.selectFile(postNo));
        return "post/postView";
    }
}
