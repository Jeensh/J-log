package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.request.SignUp;

public interface AuthService {
    /**
     * 유저 로그인
     */
    Long signIn(Login login);

    /**
     * 회원가입
     */
    void signUp(SignUp signup);
}
