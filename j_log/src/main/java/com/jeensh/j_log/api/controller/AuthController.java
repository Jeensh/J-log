package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.config.ActiveProfile;
import com.jeensh.j_log.api.request.SignUp;
import com.jeensh.j_log.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ActiveProfile activeProfile;

    @GetMapping("/login")
    public String login() {
        return "로그인 페이지입니다.";
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public void signUp(@RequestBody SignUp signUp){
        authService.signUp(signUp);
    }
}