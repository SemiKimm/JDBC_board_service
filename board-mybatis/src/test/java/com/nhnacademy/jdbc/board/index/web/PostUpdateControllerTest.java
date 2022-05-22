package com.nhnacademy.jdbc.board.index.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PostUpdateControllerTest {
    private MockMvc mockMvc;

    private PostService postService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        postService = mock(DefaultPostService.class);
        userService = mock(DefaultUserService.class);
        PostUpdateController postUpdateController =
            new PostUpdateController(postService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(postUpdateController).build();
    }


    // 로그인 한 사용자 + 작성자인 경우
    @Test
    void updatePostForm_loginUserEqualsWriter() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestAdmin()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(getTestPost_writeByUser()));
        mockMvc.perform(get("/post/update")
                .param("postNo", String.valueOf(anyInt()))
                .sessionAttr("no", 1))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postUpdateForm"))
            .andReturn();
    }

    // 작성자가 아니지만 관리자인 경우(userTypeCode 가 1 인 경우)
    @Test
    void updatePostForm_loginUserIsAdmin_andNotEqualsWriter() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestAdmin()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(getTestPost_writeByOther()));
        mockMvc.perform(get("/post/update")
                .param("postNo", String.valueOf(anyInt()))
                .sessionAttr("no", 1))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postUpdateForm"));
    }

    // 로그인했지만 작성자가 아니고 관리자도 아닌경우
    @Test
    void updatePostForm_loginUserIsNotAdmin_andNotEqualsWriter() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestUser()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(getTestPost_writeByOther()));
        mockMvc.perform(get("/post/update")
                .param("postNo", String.valueOf(anyInt()))
                .sessionAttr("no", 2))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/postView?no=" + anyInt()));
    }

    // 로그인한 사용자 + 작성자인 경우
    @Test
    void doUpdatePost_loginUser_isWriter() throws Exception {
        Post post = getTestPost_writeByUser();
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestAdmin()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(post));
        when(postService.updatePost(post)).thenReturn(1);

        mockMvc.perform(post("/post/update")
                .param("no", String.valueOf(1))
                .param("postTitle", "수정")
                .param("postContent", "hihi")
                .sessionAttr("no", 1))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/postView?no=1"));

        verify(postService,times(1)).updatePost(post);
    }

    // 작성자는 아니지만 로그인한 사용자가 관리자인 경우
    @Test
    void doUpdatePost_loginUserIsAdmin_andNotWriter() throws Exception {
        Post post = getTestPost_writeByOther();
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestAdmin()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(post));
        when(postService.updatePost(getTestPost_writeByUser())).thenReturn(1);

        mockMvc.perform(post("/post/update")
                .param("no", String.valueOf(1))
                .param("postTitle", "수정")
                .param("postContent", "hihi")
                .sessionAttr("no", 1))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/postView?no=1"));

        verify(postService,times(1)).updatePost(post);
    }

    // 로그인한 사용자가 관리자가 아니고 + 작성자가 아닌 경우
    @Test
    void doUpdatePost_loginUserIsNotAdmin_andNotWriter() throws Exception {
        Post post = getTestPost_writeByOther();
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestUser()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(post));

        mockMvc.perform(post("/post/update")
                .param("no", String.valueOf(1))
                .param("postTitle", "수정")
                .param("postContent", "hihi")
                .sessionAttr("no", 1))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/postView?no=1"));

        verify(postService,never()).updatePost(post);
    }

    private User getTestUser() {
        return new User(2, "user1", "1234", "사용자1", 2);
    }

    private User getTestAdmin() {
        return new User(1, "admin1", "1234", "관리자1", 1);
    }

    private Post getTestPost_writeByUser() {
        return new Post(1, "아무거나",
            "hi", new Timestamp(new Date().getTime()),
            0, 1, 1, null, null, null, null);
    }

    private Post getTestPost_writeByOther() {
        return new Post(1, "아무거나",
            "hi", new Timestamp(new Date().getTime()),
            0, 3, 1, null, null, null, null);
    }
}