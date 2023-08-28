package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.*;

@Slf4j
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void savePostTest() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(postCreate.getTitle());
        assertThat(post.getContent()).isEqualTo(postCreate.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void findPostByIdTest() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        Long postId = postService.write(postCreate);
        PostResponse postResponse = postService.get(postId);

        //then
        assertThat(postResponse.getId()).isEqualTo(postId);
        assertThat(postResponse.getTitle()).isEqualTo(postCreate.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(postCreate.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void findPostsForPageTest() {
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

        //when
        Pageable pageable = PageRequest.of(0, 5, Sort.by(DESC, "id"));
        List<PostResponse> posts = postService.getList(pageable);

        //then
        assertThat(posts.size()).isEqualTo(maxSizePerPage);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목입니다30");
        assertThat(posts.get(4).getTitle()).isEqualTo("제목입니다26");
    }
}