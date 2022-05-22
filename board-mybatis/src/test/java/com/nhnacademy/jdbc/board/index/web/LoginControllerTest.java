package com.nhnacademy.jdbc.board.index.web;
import static org.assertj.core.api.Assertions.assertThat;
import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.dto.UserDto;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class LoginControllerTest {

    MockMvc mockMvc;
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        LoginController controller = new LoginController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void loginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));
    }

    @Test
    void validInformDoLogin() throws Exception {
        UserDto dto = new UserDto();
        dto.setUserId("관리자1");
        dto.setUserPassword("1234");

        when(userService.getUser("관리자1","1234")).thenReturn(Optional.of(new User(1,
                "관리자1", "1234", "efef", 1)));
        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .flashAttr("userDto", dto))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Integer sessionValue =
                (Integer) mvcResult.getRequest().getSession().getAttribute("no");

        assertThat(sessionValue).isEqualTo(1);
    }


    @Test
    void invalidInformDoLogin() throws Exception {
        when(userService.getUser("관리자1","1234")).thenReturn(null);
        mockMvc.perform(post("/login")
                        .param("userDto", String.valueOf(new UserDto())))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"))
                .andReturn();
    }
}