package com.nhnacademy.jdbc.board.index.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PostDeleteControllerTest {
    private MockMvc mockMvc;

    private PostService postService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        userService = mock(UserService.class);
        PostDeleteController postDeleteController =
            new PostDeleteController(postService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(postDeleteController).build();
    }

    // 로그인한 사용자 + 작성자인경우
    @Test
    void doDeletePost_loginUser_isWriter() throws Exception {
        Post post = getTestPost_writeByUser();
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestAdmin()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(post));
        when(postService.deletePost(post.getPostNo())).thenReturn(1);

        mockMvc.perform(get("/post/delete")
            .param("postNo", String.valueOf(anyInt()))
            .sessionAttr("no", 1))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));

        verify(postService,times(1)).deletePost(anyInt());
    }

    // 로그인한 사용자 + 작성자가 아닌경우
    @Test
    void doDeletePost_loginUser_isNotWriter() throws Exception {
        Post post = getTestPost_writeByOther();
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(getTestUser()));
        when(postService.getPost(anyInt())).thenReturn(Optional.of(post));

        mockMvc.perform(get("/post/delete")
                .param("postNo", String.valueOf(2))
                .sessionAttr("no", 2))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/postView?no=2"));

        verify(postService,never()).deletePost(post.getPostNo());
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
        return new Post(2, "아무거나",
            "hi", new Timestamp(new Date().getTime()),
            0, 3, 1, null, null, null, null);
    }
}