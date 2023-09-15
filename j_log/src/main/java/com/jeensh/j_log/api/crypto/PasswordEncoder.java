package com.jeensh.j_log.api.crypto;

public interface PasswordEncoder {

    /**
     * 비밀번호 암호화
     * 반환값 : String(암호화된 비밀번호)
     */
    String encrypt(String password);

    /**
     * 비밀번호 대조
     * 반환값 : Boolean(비밀번호와 암호화된 비밀번호의 원본이 일치하는지)
     */
    Boolean matches(String password, String encryptedPassword);
}
