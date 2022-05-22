package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PostGoodControllerTest {

    private MockMvc mockMvc;
    private GoodService goodService;
    private CommentService commentService;
    private FileService fileService;
    private PostService postService;

    @BeforeEach
    void setUp(){
        goodService = mock(GoodService.class);
        commentService = mock(CommentService.class);
        fileService = mock(FileService.class);
        postService = mock(PostService.class);
        PostGoodController postGoodController = new PostGoodController(goodService,postService,commentService,fileService);
        mockMvc = MockMvcBuilders.standaloneSetup(postGoodController).build();
    }
    @Test
    @DisplayName("good을 이미 눌렀을 경우")
    void userGoodToPost() throws Exception {
        int postNo = 1;
        int userNo = 1;
        when(postService.getPost(postNo)).thenReturn(Optional.of(getTestPost()));
        when(commentService.getComments(postNo)).thenReturn(null);
        when(goodService.isUserGoodToPost(postNo,userNo)).thenReturn(true);
        when(goodService.deleteGood(postNo,userNo)).thenReturn(1);
        when(goodService.getGoodCount(postNo)).thenReturn(0);

        mockMvc.perform(get("/post/postView/isUserGoodToPost")
                .param("postNo",String.valueOf(postNo))
                .sessionAttr("no",userNo))
                .andExpect(status().isOk())
                .andExpect(view().name("post/postView"));
    }
    @Test
    @DisplayName("good을 안 눌렀을 경우")
    void userNotGoodToPost() throws Exception {
        int postNo = 1;
        int userNo = 1;
        when(postService.getPost(postNo)).thenReturn(Optional.of(getTestPost()));
        when(commentService.getComments(postNo)).thenReturn(null);
        when(goodService.isUserGoodToPost(postNo,userNo)).thenReturn(false);
        when(goodService.insertGood(postNo,userNo)).thenReturn(1);
        when(goodService.getGoodCount(postNo)).thenReturn(1);

        mockMvc.perform(get("/post/postView/isUserGoodToPost")
                        .param("postNo",String.valueOf(postNo))
                        .sessionAttr("no",userNo))
                .andExpect(status().isOk())
                .andExpect(view().name("post/postView"));
    }

    private Post getTestPost() {
        return new Post(1, "아무거나",
                "hi", new Timestamp(new Date().getTime()),
                0, 1, 1, null, null, null, null);
    }


}