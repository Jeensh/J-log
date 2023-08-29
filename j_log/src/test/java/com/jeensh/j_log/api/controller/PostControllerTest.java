package com.jeensh.j_log.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("/post 요청시 DB에 값 저장")
    void savePostRequestTest() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //expected
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(request.getTitle());
        assertThat(post.getContent()).isEqualTo(request.getContent());
    }

    @Test
    @DisplayName("/posts/{postId} 요청시 postId에 해당하는 게시글 조회")
    void findPostRequestTest() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        Long postId = postService.write(postCreate);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(postCreate.getTitle()))
                .andExpect(jsonPath("$.content").value(postCreate.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts?page=1 요청시 게시글 목록 1페이지 조회")
    void findPostsForPageTest() throws Exception {
        //given
        int count = 30;
        int maxSizePerPage = 5;
        List<Post> requestPosts = new ArrayList<>();
        for(int i = 1; i <= count; i++){
            Post post = Post.builder()
                    .title("제목입니다" + i)
                    .content("내용입니다" + i)
                    .build();
            requestPosts.add(post);
        }
        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(maxSizePerPage))
                .andExpect(jsonPath("$[0].title").value(requestPosts.get(29).getTitle()))
                .andExpect(jsonPath("$[0].content").value(requestPosts.get(29).getContent()))
                .andExpect(jsonPath("$[1].title").value(requestPosts.get(28).getTitle()))
                .andExpect(jsonPath("$[1].content").value(requestPosts.get(28).getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts?page=0 요청시 첫 페이지 조회")
    void RequestPageZeroTest() throws Exception {
        //given
        int count = 30;
        int maxSizePerPage = 5;
        List<Post> requestPosts = new ArrayList<>();
        for(int i = 1; i <= count; i++){
            Post post = Post.builder()
                    .title("제목입니다" + i)
                    .content("내용입니다" + i)
                    .build();
            requestPosts.add(post);
        }
        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(maxSizePerPage))
                .andExpect(jsonPath("$[0].title").value(requestPosts.get(29).getTitle()))
                .andExpect(jsonPath("$[0].content").value(requestPosts.get(29).getContent()))
                .andExpect(jsonPath("$[1].title").value(requestPosts.get(28).getTitle()))
                .andExpect(jsonPath("$[1].content").value(requestPosts.get(28).getContent()))
                .andDo(print());
    }
}