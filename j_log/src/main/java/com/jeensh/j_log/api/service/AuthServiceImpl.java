package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.exception.InvalidSigninInformation;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.request.Login;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;

    /**
     * 유저 로그인
     */
    public Long signIn(Login login) {
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return member.getId();
    }
}
