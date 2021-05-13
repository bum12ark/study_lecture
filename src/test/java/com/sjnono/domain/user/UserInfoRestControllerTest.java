package com.sjnono.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        UserInfo userInfo = new UserInfo();
        userInfo.setName("유저이름" + index);
        userInfo.setEmail("test" + index + "@google.com");
        userInfo.setPassword(String.valueOf(index));
        return userInfo;
    }

    @Test
    public UserInfo createUser() throws Exception {
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

        return userInfo;
    }

    @Test
    public void createUser_badRequest() throws Exception {
        // Given
        UserInfoDto userInfoDto = UserInfoDto.builder().build();

        // When & Then
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfoDto))
        )  // 요청
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].code").exists())
        ;
    }

    @Test
    public void getUser() throws Exception {
        // Given
        UserInfo userInfo = this.generateUser(100);

        // When
        this.mockMvc.perform(get("/{email}", userInfo.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
    }

    @Test
    public void login() throws Exception {
        // Given
        UserInfo user = this.createUser();
        String email = user.getEmail();

        // When
        this.mockMvc.perform(
                post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print());

    }
}