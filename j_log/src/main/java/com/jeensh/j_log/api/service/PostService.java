package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Post;
import com.jeensh.j_log.api.repository.PostRepository;
import com.jeensh.j_log.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        postRepository.save(new Post(postCreate.getTitle(), postCreate.getContent()));
    }
}
