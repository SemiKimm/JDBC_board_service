package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class CommentControllerTest {

    private MockMvc mockMvc;
    private CommentService commentService;

    @BeforeEach
    void setUp(){
        commentService = mock(CommentService.class);
        CommentController controller = new CommentController(commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void doRegisterComment() throws Exception {
        String commentContent = "123";
        int postNo = 1;
        when(commentService.addComment(new Comment(commentContent,postNo,1))).thenReturn(1);

        mockMvc.perform(post("/comment/register")
                .param("postNo",String.valueOf(1))
                .param("commentContent",commentContent)
                        .sessionAttr("no",1))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void modifyCommentForm() throws Exception {
        int commentNo = 1;
        Comment comment = new Comment(1,"123",1,1);
        when(commentService.getComment(commentNo)).thenReturn(Optional.of(comment));
        mockMvc.perform(get("/comment/modify")
                .param("commentNo",String.valueOf(commentNo))
                .sessionAttr("no",1))
                .andExpect(status().isOk())
                .andExpect(view().name("/comment/commentModifyForm"));
    }

    @Test
    void doModifyComment() throws Exception {
        int commentNo = 1;
        String commentContent = "123";
        int postNo = 1;
        Comment comment = new Comment(1,"123",1,1);
        when(commentService.getComment(commentNo)).thenReturn(Optional.of(comment));
        mockMvc.perform(post("/comment/modify")
                        .param("commentNo",String.valueOf(commentNo))
                        .param("commentContent",commentContent)
                        .param("postNo",String.valueOf(postNo))
                        .sessionAttr("no",1))
                .andExpect(status().is3xxRedirection());

    }


    @Test
    void doDeleteComment() throws Exception {
        int commentNo = 1;
        int postNo = 1;
        when(commentService.deleteComment(commentNo)).thenReturn(1);
        mockMvc.perform(get("/comment/delete")
                        .param("commentNo",String.valueOf(commentNo))
                        .param("postNo",String.valueOf(postNo)))
                .andExpect(status().is3xxRedirection());
    }
}