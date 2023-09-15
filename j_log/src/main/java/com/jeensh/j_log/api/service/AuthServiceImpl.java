package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.exception.AlreadyExistsEmailException;
import com.jeensh.j_log.api.exception.InvalidSigninInformation;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.request.SignUp;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * 회원가입
     */
    public void signUp(SignUp signup) {
        Optional<Member> member = memberRepository.findByEmail(signup.getEmail());
        if(member.isPresent()){
            throw new AlreadyExistsEmailException();
        }

        Member newMember = Member.builder()
                .name(signup.getName())
                .password(signup.getPassword())
                .email(signup.getEmail())
                .build();
        memberRepository.save(newMember);
    }
}
