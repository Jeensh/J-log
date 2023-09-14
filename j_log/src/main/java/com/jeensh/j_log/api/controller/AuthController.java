package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.config.ActiveProfile;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.response.SessionResponse;
import com.jeensh.j_log.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ActiveProfile activeProfile;

    /**
     * 유저 로그인 - jwt 방식
     */
    @PostMapping("/login")
    public SessionResponse login(@RequestBody Login login) {
        Long memberId = authService.signIn(login);

        SecretKey key = Keys.hmacShaKeyFor(activeProfile.getJwtKey());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date expirationTime = calendar.getTime();

        String jws = Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .compact();

        return new SessionResponse(jws);
    }
}