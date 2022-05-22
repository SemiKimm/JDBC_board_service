package com.nhnacademy.jdbc.board.index.web;

import com.mysql.cj.Session;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class IndexControllerTest {
//    //test target SUT
//    controller
//    // doc
//    service mocking;
    MockMvc mockMvc;
    UserService userService;
    MockHttpSession session = new MockHttpSession();

    private MockHttpSession getMockHttpSession() {
        session.setAttribute("no", 1);
        return session;
    }

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        IndexController controller = new IndexController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    //user 서비스를 모킹한다.
    //mocking한 userservice를 controller에 꽂아준다.
    //이제 userservice의
    @Test
    @DisplayName("session에 userNo가 있는 경우")
    void indexPageLoginUser() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(
                Optional.of(getTestUser()));
        MvcResult mvcResult = mockMvc.perform(get("/")
                .sessionAttr("no",1))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNotNull()
                .isEqualTo(1);
    }

    @Test
    @DisplayName("session에 userNo가 없는 경우")
    void indexPageNotLoginUser() throws Exception {
        when(userService.getUserByNo(anyInt())).thenReturn(
                Optional.of(getTestUser()));
        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }

    @Test
    @DisplayName("login 한 유저가 logout 버튼을 눌렀을 시")
    void loginUserClickLogoutTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout")
                        .param("no",String.valueOf(anyInt()))
                        .param("login", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }

    @Test
    @DisplayName("login 안 한 유저가 logout 버튼을 눌렀을 시")
    void notLoginUserClickLogoutTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout")
                        .param("login", String.valueOf(false)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }
    @Test
    @DisplayName("session이 없는 경우")
    void notLoginUserHasNoSession() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout")
                        .param("login", String.valueOf(false)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }
    @Test
    @DisplayName("session이 있고 no attribute가 없는 경우")
    void notLoginUserHasSessionAndNoParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout").session(session)
                        .param("login", String.valueOf(false)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }
    @Test
    @DisplayName("session이 있고 no attribute가 있고 로그인 안 한 경우")
    void notLoginUserHasSessionAndHasParamAndNotLogin() throws Exception {
        when(session.getAttribute("no")).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get("/logout").session(session)
                        .param("login", String.valueOf(false)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
    }

    @Test
    @DisplayName("session이 있고 no attribute가 있고 로그인  한 경우")
    void notLoginUserHasSessionAndHasParamAndLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/logout").session(session)
                        .param("login", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(view().name("/index/index"))
                .andReturn();

        Integer loginUserNo = (Integer) mvcResult.getRequest().getSession().getAttribute("no");
        assertThat(loginUserNo).isNull();
//        assertThat(session.isInvalid()).isTrue();
    }
    private User getTestUser() {
        return new User(1, "admin1", "1234", "관리자1", 1);
    }


}