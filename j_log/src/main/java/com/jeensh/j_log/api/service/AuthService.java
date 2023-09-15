package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.request.Login;

public interface AuthService {
    /**
     * 유저 로그인
     */
    Long signIn(Login login);
}
