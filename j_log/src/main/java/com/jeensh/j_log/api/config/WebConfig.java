package com.jeensh.j_log.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.jeensh.j_log.api.config.CorsAllowOrigin.AWS_FRONT;
import static com.jeensh.j_log.api.config.CorsAllowOrigin.LOCAL_FRONT;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ActiveProfile activeProfile;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCAL_FRONT, AWS_FRONT)
                .allowedMethods("*");
    }
}
