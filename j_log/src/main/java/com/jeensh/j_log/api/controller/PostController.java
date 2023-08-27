package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.request.PostCreate;
import com.jeensh.j_log.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

//    @PostMapping("/posts")
//    public String post_v1(@RequestParam String title, @RequestParam String content) {
//        log.info("title={}, content={}", title, content);
//        return "title=" + title + ", content=" + content;
//    }

//    @PostMapping("/posts")
//    public String post_v2(@RequestParam Map<String, String> params) {
//        log.info("params={}", params);
//        return "title=" + params.get("title") + ", content=" + params.get("content");
//    }

//    @PostMapping("/posts")
//    public String post_v3(@ModelAttribute PostCreate params) {
//        log.info("params={}", params);
//        return params.toString();
//    }

//    @PostMapping("/posts")
//    public String post_v4(@RequestBody @Validated PostCreate params) {
//        log.info("params={}", params);
//        return params.toString();
//    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Validated PostCreate request) {
        postService.write(request);
        return Map.of();
    }
}
