package com.jeensh.j_log.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Getter
@Setter
@ConfigurationProperties(prefix = "server")
public class ActiveProfile {

    private String address;
    private byte[] jwtKey;

    public void setJwtKey(String jwtKey){
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

}
