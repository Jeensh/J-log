package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.crypto.PasswordEncoder;
import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.exception.AlreadyExistsEmailException;
import com.jeensh.j_log.api.exception.InvalidSigninInformation;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.request.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    /**
     * 유저 로그인
     */
    @Transactional(readOnly = true)
    public Long signIn(Login login) {
        Member member = memberRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        boolean matches = encoder.matches(login.getPassword(), member.getPassword());
        if (!matches){
            throw new InvalidSigninInformation();
        }

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

        String encryptedPassword = encoder.encrypt(signup.getPassword());

        Member newMember = Member.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();
        memberRepository.save(newMember);
    }
}
