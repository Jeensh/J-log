package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.config.ActiveProfile;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final ActiveProfile activeProfile;

    /**
     * 유저 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Login login) {
        String accessToken = authService.signIn(login);

        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain(activeProfile.getAddress())
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
