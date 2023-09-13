package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.response.SessionResponse;
import com.jeensh.j_log.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 유저 로그인
     */
    @PostMapping("/login")
    public SessionResponse login(@RequestBody Login login) {
        String accessToken = authService.signIn(login);
        return new SessionResponse(accessToken);
    }
}
