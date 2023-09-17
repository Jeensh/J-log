package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.config.ActiveProfile;
import com.jeensh.j_log.api.request.SignUp;
import com.jeensh.j_log.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 회원가입
     */
    @PostMapping("/signup")
    public void signUp(@RequestBody SignUp signUp){
        authService.signUp(signUp);
    }

    private static Date getTomorrow() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date expirationTime = calendar.getTime();
        return expirationTime;
    }
}