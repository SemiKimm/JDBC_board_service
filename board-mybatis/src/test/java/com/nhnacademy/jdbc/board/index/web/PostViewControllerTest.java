package com.nhnacademy.jdbc.board.index.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.comment.service.impl.DefaultCommentService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.file.service.impl.DefaultFileService;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import com.nhnacademy.jdbc.board.good.service.impl.DefaultGoodService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.post.service.impl.DefaultPostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
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

    // login 한 user 의 경우
    @Test
    void viewPage_loginUser() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(
            Optional.of(new User(anyInt(), anyString(), anyString(), anyString(), anyInt())));
        mockMvc.perform(get("/post/postView")
            .sessionAttr("no",anyInt()))
            .andExpect(status().isOk());

    }
}