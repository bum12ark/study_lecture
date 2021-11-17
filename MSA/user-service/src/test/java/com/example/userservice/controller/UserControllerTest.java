package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void createUserSuccess() throws Exception {
        // Given
        RequestUser requestUser = new RequestUser();
        requestUser.setEmail("testId@gmail.com");
        requestUser.setName("홍길동");
        requestUser.setPwd("testPassword");

        User saveUser = User.builder()
                .userId(UUID.randomUUID().toString())
                .name(requestUser.getName())
                .encryptedPwd(UUID.randomUUID().toString())
                .build();

        given(userService.createUser(requestUser)).willReturn(saveUser);

        String requestJson = objectMapper.writeValueAsString(requestUser);

        // When & Then
        mockMvc.perform(
                post("/user-service/users")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 잘못된 파라미터")
    void createUserBadParameter() throws Exception {
        // Given
        RequestUser requestUser = new RequestUser();
        requestUser.setEmail("not_email");
        requestUser.setName("두자");
        requestUser.setPwd("errorPw");

        String requestJson = objectMapper.writeValueAsString(requestUser);

        // When & Then
        mockMvc.perform(
                post("/user-service/users")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("모든 유저 조회")
    void getUsers() throws Exception {
        // Given
        // When & Then
        mockMvc.perform(get("/user-service/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("특정 유저 조회")
    void getUser() throws Exception {
        // Given
        String userId = "testId";

        given(userService.getUserByUserId(userId)).willReturn(User.builder().userId("testId").build());

        // When & Then
        mockMvc.perform(get("/user-service/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("testId"))
                .andDo(print());
    }
}