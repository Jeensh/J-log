package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.config.data.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
//@Profile("test")
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    /**
     * 인증/인가 테스트용 임시 핸들러
     */
    @GetMapping
    public String test(MemberSession memberSession){
        return memberSession.getId().toString();
    }
}
