package com.jeensh.j_log.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/post에 GET 요청시 Hello World를 출력한다.")
    void checkGetApi_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

//    사용안함
//    @Test
//    @DisplayName("/post에 POST 요청시 전달된 파라미터를 출력한다 - 요청 파라미터")
//    void checkPostApi_RequestParam_success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("title", "글 제목입니다.")
//                        .param("content", "글 내용입니다.")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string("title=글 제목입니다., content=글 내용입니다."))
//                .andDo(print());
//    }

//    사용안함
//    @Test
//    @DisplayName("/post에 POST 요청시 전달된 파라미터를 출력한다 - JSON")
//    void checkPostApi_JSON_success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": \"글 제목입니다.\", \"content\": \"글 내용입니다.\"}")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string("title=글 제목입니다., content=글 내용입니다."))
//                .andDo(print());
//    }

    @Test
    @DisplayName("/post에 POST 요청시 title 값은 필수다 - 데이터 검증")
    void checkPostApi_verification_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"content\": \"글 내용입니다.\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요!"))
                .andExpect(jsonPath("$.validation.content").value("컨텐츠를 입력해주세요!"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목\", \"content\": \"글 내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}