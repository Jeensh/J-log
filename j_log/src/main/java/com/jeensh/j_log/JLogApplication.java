package com.jeensh.j_log;

import com.jeensh.j_log.api.config.ActiveProfile;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@EnableConfigurationProperties(ActiveProfile.class)
@SpringBootApplication
public class JLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JLogApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public TestDataInit testDataInit(MemberRepository memberRepository, PostRepository postRepository){
        return new TestDataInit(postRepository, memberRepository);
    }
}
