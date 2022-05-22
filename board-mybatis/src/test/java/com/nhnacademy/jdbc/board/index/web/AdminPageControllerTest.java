package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AdminPageControllerTest {
//    //test target SUT
//    controller
//    // doc
//    service mocking;
    private MockMvc mockMvc;
    PostService postService;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        AdminPageController controller = new AdminPageController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void viewDeletedPosts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/post/deletedPost")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/deletedPostList"))
                .andReturn();
    }

    @Test
    void restorePosts() throws Exception {
        when(postService.restorePost(1)).thenReturn(1);
        when(postService.getDeletedPosts()).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get("/post/deletedPost/restorePost")
                        .param("deletedPostNo",String.valueOf(1))
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
    @Test
    void viewPosts() throws Exception {
        when(postService.getDeletedPost(1)).thenReturn(Optional.of(new Post(1)));
        MvcResult mvcResult = mockMvc.perform(get("/post/deletedPost/viewDeletedPost")
                        .param("deletedPostNo",String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("post/deletedPostView"))
                .andReturn();
    }

}