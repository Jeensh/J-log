package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.request.SignUp;

public interface AuthService {
    /**
     * 회원가입
     */
    void signUp(SignUp signup);
}
