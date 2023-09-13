package com.jeensh.j_log;

import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        memberRepository.save(Member.builder()
                .name("신동해")
                .email("jeensh25@gmail.com")
                .password("1234")
                .build());
    }
}
