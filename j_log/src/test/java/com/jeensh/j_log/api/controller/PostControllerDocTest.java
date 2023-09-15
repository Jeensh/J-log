package com.jeensh.j_log.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.jlog.com", uriPort = 443)
@AutoConfigureMockMvc
@Transactional
public class PostControllerDocTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("post 단건 조회 테스트")
    void test1() throws Exception{
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        Long postId = postService.write(postCreate);

        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", postId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("postId").description("게시글 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용")
                        )
                ));
    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception{
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목")
                                        .attributes(Attributes.key("constraint").value("제목에 '바보'를 포함할 수 없습니다")),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용").optional()
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID")
                        )
                ));
    }
}
