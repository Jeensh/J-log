package com.jeensh.j_log.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 테스트를 위한 MockPasswordEncoder
 */
@Component
@Profile({"test", "dev"})
public class MockPasswordEncoder implements PasswordEncoder{

    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public Boolean matches(String password, String encryptedPassword) {
        return password.equals(encryptedPassword);
    }
}
