package com.sjnono.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserInfoRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    private UserInfo generateUser(int index) {
        return UserInfo.builder()
                .email("test" + index + "@google.com")
                .name("유저이름" + index)
                .password(String.valueOf(index))
                .build();
    }

    @BeforeEach
    void init() throws Exception {
        // Given
        UserInfo userInfo = this.generateUser(100);

        // When
        ResultActions perform = mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfo))
        );
    }

    @Test
    public void findUserByEmail() throws Exception {
        // Given
        UserInfo userInfo = this.generateUser(100);

        // When
        this.mockMvc.perform(get("/user/{email}", userInfo.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
    }

    @Test
    public void findUserByEmail_NotFound() throws Exception {

        // When
        this.mockMvc.perform(get("/user/{email}", "notFound@google.com"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("httpStatus").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
    }

    @Test
    public void createUser() throws Exception {
        // Given
        UserInfo userInfo = this.generateUser(100);

        // When
        ResultActions perform = mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfo))
        );
        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void createUser_badRequest() throws Exception {
        // Given
        UserInfoDto userInfoDto = UserInfoDto
                .builder()
                .email("test@email.com")
                .build();

        // When & Then
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfoDto))
        )  // 요청
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    @Test
    public void login() throws Exception {
        // Given
        UserInfo user = this.generateUser(100);

        // When
        this.mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print());

    }
}