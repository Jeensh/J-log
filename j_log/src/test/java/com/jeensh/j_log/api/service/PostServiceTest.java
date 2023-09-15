package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.exception.PostNotFound;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.request.PostEdit;
import com.jeensh.j_log.api.request.PostSearch;
import com.jeensh.j_log.api.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostServiceImpl postService;
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
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void findPostByIdTest_PostNotFound() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        Long postId = postService.write(postCreate);

        //expected
        assertThatThrownBy(() -> postService.get(postId + 1L)).isInstanceOf(PostNotFound.class);
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void findPostsForPageTest() {
        //given
        int count = 30;
        int maxSizePerPage = 10;
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
        PostSearch postSearch = PostSearch.builder()
                .build();
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertThat(posts.size()).isEqualTo(maxSizePerPage);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목입니다30");
        assertThat(posts.get(4).getTitle()).isEqualTo("제목입니다26");
    }

    @Test
    @DisplayName("글 제목 수정")
    void editPostTitleTest() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        postRepository.save(post);

        String titleToChange = "수정 후 제목";
        PostEdit postEdit = PostEdit.builder()
                .title(titleToChange)
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertThat(changedPost.getTitle()).isEqualTo(titleToChange);
    }

    @Test
    @DisplayName("글 제목 수정 - 존재하지 않는 글")
    void editPostTitleTest_PostNotFound() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        postRepository.save(post);

        String titleToChange = "수정 후 제목";
        PostEdit postEdit = PostEdit.builder()
                .title(titleToChange)
                .build();

        //expected
        assertThatThrownBy(() ->  postService.edit(post.getId() + 1L, postEdit)).isInstanceOf(PostNotFound.class);

    }

    @Test
    @DisplayName("글 내용 수정")
    void editPostContentTest() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        postRepository.save(post);

        String contentToChange = "수정 후 내용";
        PostEdit postEdit = PostEdit.builder()
                .content(contentToChange)
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertThat(changedPost.getContent()).isEqualTo(contentToChange);
    }

    @Test
    @DisplayName("글 삭제")
    void deletePostTest() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        assertThat(postRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("글 삭제 - 존재하지 않는 글")
    void deletePostTest_PostNotFound() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        postRepository.save(post);

        //then
        assertThatThrownBy(() ->  postService.delete(post.getId() + 1L)).isInstanceOf(PostNotFound.class);
    }
}