package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.request.PostEdit;
import com.jeensh.j_log.api.request.PostSearch;
import com.jeensh.j_log.api.response.PostResponse;
import com.jeensh.j_log.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * post 저장
     */
    @PostMapping("/posts")
    public Map<String, Long> post(@RequestBody @Validated PostCreate request) {
        request.validate();
        Long postId = postService.write(request);
        return Map.of("id", postId);
    }

    /**
     *  post 단건 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    /**
     * post 목록 조회
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    /**
     * post 수정
     */
    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Validated PostEdit postEdit){
        return postService.edit(postId, postEdit);
    }

    /**
     * post 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
