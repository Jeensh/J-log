package com.jeensh.j_log.api.controller;

import com.jeensh.j_log.api.domain.User;
import com.jeensh.j_log.api.exception.InvalidSigninInformation;
import com.jeensh.j_log.api.repository.UserRepository;
import com.jeensh.j_log.api.request.Login;
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

    private final UserRepository userRepository;

    /**
     * 유저 로그인
     */
    @PostMapping("/login")
    public void login(@RequestBody Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(() -> new InvalidSigninInformation());
    }
}
