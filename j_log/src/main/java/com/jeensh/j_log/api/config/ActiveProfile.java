package com.jeensh.j_log.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ActiveProfile {

    @Value("${server.address}")
    private String address;

}
