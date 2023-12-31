package com.jeensh.j_log.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.domain.Session;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.repository.SessionRepository;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.request.SignUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("/auth/login POST 요청시 로그인 성공 후 jwt 응답")
    void loginTest() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .name("유저1")
                .email("user1@gmail.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("user1@gmail.com")
                .password("1234")
                .build();

        String json_login = objectMapper.writeValueAsString(login);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_login)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("/auth/login POST 요청에서 이메일 및 비밀번호 오기입시 로그인 실패")
    void loginTest_fail() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .name("유저1")
                .email("user1@gmail.com")
                .password("1234")
                .build());

        Login login_wrong_email = Login.builder()
                .email("user2@gmail.com")
                .password("1234")
                .build();

        Login login_wrong_password = Login.builder()
                .email("user1@gmail.com")
                .password("123")
                .build();

        String json_wrong_email = objectMapper.writeValueAsString(login_wrong_email);
        String json_wrong_password = objectMapper.writeValueAsString(login_wrong_password);

        //expected

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_wrong_email)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("아이디/비밀번호가 올바르지 않습니다."))
                .andDo(print());

        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json_wrong_password)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("아이디/비밀번호가 올바르지 않습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 인증이 필요한 페이지 접속 /test")
    void authAfterLogin() throws Exception {
        //given
        Member member = Member.builder()
                .name("유저1")
                .email("user1@gmail.com")
                .password("1234")
                .build();
        Session session = member.addSession();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("user1@gmail.com")
                .password("1234")
                .build();

        String json_login = objectMapper.writeValueAsString(login);

        ResultActions ra = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json_login)
        );

        String accessToken = ra.andReturn().getResponse().getContentAsString();
        String accessTokenValue = JsonPath.read(accessToken, "$.accessToken");

        //expect
        mockMvc.perform(get("/test")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", accessTokenValue)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 검증되지 않은 세션값으로 권한이 필요한 페이지 접근 불가 /test")
    void authAfterLogin_fail() throws Exception {
        //given
        Member member = Member.builder()
                .name("유저1")
                .email("user1@gmail.com")
                .password("1234")
                .build();
        Session session = member.addSession();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("user1@gmail.com")
                .password("1234")
                .build();

        String json_login = objectMapper.writeValueAsString(login);

        ResultActions ra = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json_login)
        );

        String accessToken = ra.andReturn().getResponse().getContentAsString();
        String accessTokenValue = JsonPath.read(accessToken, "$.accessToken");

        //expect
        mockMvc.perform(get("/test")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", accessTokenValue + "1")
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("/auth/signup post 요청시 회원가입")
    void signUp() throws Exception {
        //given
        SignUp signUp = SignUp.builder()
                .name("jeensh")
                .email("jeensh25@gmail.com")
                .password("1234")
                .build();

        //expect
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}