package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.request.PostSearch;
import com.jeensh.j_log.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 저장
     */
    public Long write(PostCreate postCreate) {
        Post post = postRepository.save(Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build());

        return post.getId();
    }

    /**
     * 단건조회
     */
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return new PostResponse(post);
    }

    /**
     * 페이지 조회
     */
    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
