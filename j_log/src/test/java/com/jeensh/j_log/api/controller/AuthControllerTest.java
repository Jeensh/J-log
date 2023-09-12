package com.jeensh.j_log.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeensh.j_log.api.repository.UserRepository;
import com.jeensh.j_log.api.request.Login;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("/auth/login POST 요청시 로그인")
    void savePostRequestTest() throws Exception {
        //given
        Login login = Login.builder()
                .email("jeensh25@gmail.com")
                .password("1234")
                .build();

        Login login_wrong_email = Login.builder()
                .email("jeensh252@gmail.com")
                .password("1234")
                .build();

        Login login_wrong_password = Login.builder()
                .email("jeensh25@gmail.com")
                .password("123")
                .build();

        String json_login = objectMapper.writeValueAsString(login);
        String json_wrong_email = objectMapper.writeValueAsString(login_wrong_email);
        String json_wrong_password = objectMapper.writeValueAsString(login_wrong_password);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_login)
                )
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_wrong_email)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_wrong_password)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}