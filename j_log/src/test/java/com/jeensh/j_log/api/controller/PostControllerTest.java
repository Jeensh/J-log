package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

//    사용안함
//    @Test
//    @DisplayName("/post에 GET 요청시 Hello World를 출력한다.")
//    void checkGetApi_success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello World"))
//                .andDo(print());
//    }

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
    @DisplayName("/post 요청시 DB에 값 저장")
    void postSaveTest_success() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목입니다");
        assertThat(post.getContent()).isEqualTo("내용입니다");
    }
}