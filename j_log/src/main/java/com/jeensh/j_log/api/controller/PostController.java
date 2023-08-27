package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.response.PostResponse;
import com.jeensh.j_log.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     *  게시글 단건 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     *  게시글 저장
     */
    @PostMapping("/posts")
    public Map<String, Long> post(@RequestBody @Validated PostCreate request) {
        Long postId = postService.write(request);
        return Map.of("postId", postId);
    }

}
