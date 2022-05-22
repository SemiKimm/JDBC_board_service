package com.nhnacademy.jdbc.board.index.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.comment.service.impl.DefaultCommentService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.file.service.impl.DefaultFileService;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import com.nhnacademy.jdbc.board.good.service.impl.DefaultGoodService;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.post.service.impl.DefaultPostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PostViewControllerTest {
    private MockMvc mockMvc;

    private PostService postService;
    private CommentService commentService;
    private GoodService goodService;
    private FileService fileService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        postService = mock(DefaultPostService.class);
        commentService = mock(DefaultCommentService.class);
        goodService = mock(DefaultGoodService.class);
        fileService = mock(DefaultFileService.class);
        userService = mock(DefaultUserService.class);
        PostViewController postViewController = new PostViewController(
            postService, commentService, goodService, fileService, userService
        );
        mockMvc = MockMvcBuilders.standaloneSetup(postViewController).build();
    }

    @DisplayName("login 한 user 의 경우에 게시글 상세화면 보기") 
    @Test
    void viewPage_loginUser() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(
            Optional.of(getTestUser()));
        when(postService.getPost(anyInt())).thenReturn(
            Optional.of(getTestPost()));
        MvcResult mvcResult = mockMvc.perform(get("/post/postView")
                .param("no", String.valueOf(anyInt()))
                .sessionAttr("no", 1))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postView"))
            .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNotNull()
            .isEqualTo(1);
    }

    @DisplayName("login 안한 user 의 경우에 게시글 상세화면 보기")
    @Test
    void viewPage_notLoginUser() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(
            Optional.of(getTestUser()));
        when(postService.getPost(anyInt())).thenReturn(
            Optional.of(getTestPost()));
        MvcResult result = mockMvc.perform(get("/post/postView")
                .param("no", String.valueOf(anyInt())))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postView"))
            .andReturn();

        Integer loginUserNo = (Integer) result.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }

    private User getTestUser() {
        return new User(1, "admin1", "1234", "관리자1", 1);
    }

    private Post getTestPost() {
        return new Post(1, "아무거나",
            "hi", new Timestamp(new Date().getTime()),
            0, 1, 1, null, null, null, null);
    }
}