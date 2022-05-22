package com.nhnacademy.jdbc.board.index.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.post.dto.PostListDTO;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PostControllerTest {
    private MockMvc mockMvc;

    private PostService postService;
    private UserService userService;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        userService = mock(UserService.class);
        fileService = mock(FileService.class);
        PostController postController = new PostController(postService, userService, fileService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    // 로그인 안했을때 + page param 이 있을때 + 검색어 없을때
    @Test
    void viewPostList_userIsNotLogin_searchKeywordIsNull_pageIsExist() throws Exception {
        when(postService.getPagePostList(null, 1, 20))
            .thenReturn(new ArrayList<>());
        when(postService.getLastPageSize(20, null))
            .thenReturn(0);

        mockMvc.perform(get("/post/list")
                .param("page", String.valueOf(1)))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postList"));

        verify(userService, never()).getUserByNo(anyInt());
    }

    // 로그인했을때 + 검색어 없을때
    @Test
    void viewPostList_userIsLogin_searchKeywordIsNull_pageIsExist() throws Exception {
        User user = getTestUser();
        when(postService.getPagePostList(null, 1, 20))
            .thenReturn(getTestPostList());
        when(postService.getLastPageSize(20, null))
            .thenReturn(0);
        when(userService.getUserByNo(anyInt())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/post/list")
                .sessionAttr("no", 2)
                .param("page", String.valueOf(1)))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postList"))
            .andExpect(model().attribute("userTypeCode", user.getUserTypeCode()))
            .andReturn();

        verify(userService, times(1)).getUserByNo(anyInt());
    }

    @Test
    void doSearchByTitle() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/post/list/search")
                .param("searchKeyword", "Hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"))
            .andReturn();
        String sessionValue =
            (String) mvcResult.getRequest().getSession().getAttribute("searchKeyword");

        assertThat(sessionValue).isNotNull()
            .isEqualTo("Hello");
    }

    @Test
    void searchAllList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/post/list/search/all")
                .sessionAttr("searchKeyword", "Hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"))
            .andReturn();
        String sessionValue =
            (String) mvcResult.getRequest().getSession().getAttribute("searchKeyword");

        assertThat(sessionValue).isNull();
    }

    // 로그인 안한 사용자의 경우
    @Test
    void viewGoodPostList_userIsNotLogin() throws Exception {
        mockMvc.perform(get("/post/list/good")
                .param("page", String.valueOf(1)))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));
    }

    // 로그인 한 사용자의 경우
    @Test
    void viewGoodPostList_userIsLogin() throws Exception {
        when(postService.getGoodPostList(2, 1, 20))
            .thenReturn(new ArrayList<>());
        when(postService.getGoodPostsLastPageSize(2, 20))
            .thenReturn(0);

        mockMvc.perform(get("/post/list/good")
                .sessionAttr("no", 2)
                .param("page", String.valueOf(1)))
            .andExpect(status().isOk())
            .andExpect(view().name("/post/goodPostList"));
    }

    // 로그인 안한 사용자의 경우
    @Test
    void registerPostForm_userIsNotLogin() throws Exception {
        mockMvc.perform(get("/post/register"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));
    }

    // 로그인한 사용자의 경우
    @Test
    void registerPostForm_userIsLogin() throws Exception {
        mockMvc.perform(get("/post/register")
                .sessionAttr("no", 2))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postForm"));
    }

    // 로그인 안한 사용자의 경우
    @Test
    void doRegisterPost_userIsNotLogin() throws Exception {
        mockMvc.perform(post("/post/register")
                .param("postTitle", "hi")
                .param("postContent", "hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));

        verify(postService, never()).registerPost(anyInt(), anyString(), anyString());
    }

    // 로그인 한 사용자 + file 없는 경우
    @Test
    void doRegisterPost_userIsLogin_fileIsNull() throws Exception {
        mockMvc.perform(post("/post/register")
                .sessionAttr("no", 2)
                .param("postTitle", "hi")
                .param("postContent", "hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));

        verify(postService, times(1)).registerPost(2, "hi", "hello");
        verify(fileService, never()).saveFile(any());
    }

    // 로그인 한 사용자 + file 있는 경우
    @Test
    void doRegisterPost_userIsLogin_fileIsExist() throws Exception {
        MockMultipartFile file =
            new MockMultipartFile("file", "test.txt", "multipart/form-data",
                new FileInputStream(
                    "D:\\NHN_Academy\\lectureDatabaseJdbc\\jdbcTeamProject\\jdbc_team_pj\\board-mybatis\\src\\test\\resources\\test.txt"));

        mockMvc.perform(
                multipart("/post/register")
                    .file(file)
                    .part(new MockPart("id", "foo".getBytes(
                        StandardCharsets.UTF_8)))
                    .sessionAttr("no", 2)
                    .param("postTitle", "hi")
                    .param("postContent", "hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/post/list"));

        verify(postService, times(1)).registerPost(2, "hi", "hello");
        verify(fileService, times(1)).saveFile(any());
    }

    private List<PostListDTO> getTestPostList() {
        return new ArrayList<>();
    }

    private User getTestUser() {
        return new User(2, "user1", "1234", "사용자1", 2);
    }
}