package com.jeensh.j_log.api.crypto;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ScryptPasswordEncoder implements PasswordEncoder {

    private static final SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64);

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword.toString(), encodedPassword);
    }
}
