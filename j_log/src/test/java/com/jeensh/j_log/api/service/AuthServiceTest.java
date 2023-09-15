package com.jeensh.j_log.api.service;

import com.jeensh.j_log.api.crypto.PasswordEncoder;
import com.jeensh.j_log.api.domain.Member;
import com.jeensh.j_log.api.exception.AlreadyExistsEmailException;
import com.jeensh.j_log.api.exception.InvalidSigninInformation;
import com.jeensh.j_log.api.repository.MemberRepository;
import com.jeensh.j_log.api.request.Login;
import com.jeensh.j_log.api.request.SignUp;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        //given
        Member member = Member.builder()
                .name("jeensh")
                .email("jeensh25@gmail.com")
                .password(passwordEncoder.encrypt("1234"))
                .build();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("jeensh25@gmail.com")
                .password("1234")
                .build();

        //when
        Long memberId = authService.signIn(login);

        //then
        assertThat(memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new)
                .getEmail()).isEqualTo("jeensh25@gmail.com");
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void login_WrongPassword() {
        //given
        Member member = Member.builder()
                .name("jeensh")
                .email("jeensh25@gmail.com")
                .password(passwordEncoder.encrypt("1234"))
                .build();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("jeensh25@gmail.com")
                .password("12345")
                .build();

        //expected
        assertThatThrownBy(() -> authService.signIn(login)).isInstanceOf(InvalidSigninInformation.class);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
        //given
        SignUp signUp = SignUp.builder()
                .name("jeensh")
                .email("jeensh25@gmail.com")
                .password("1234")
                .build();

        //when
        authService.signUp(signUp);

        //then
        assertThat(memberRepository.count()).isEqualTo(1);
        assertThat(memberRepository.findByEmail("jeensh25@gmail.com").orElseThrow(NoSuchElementException::new)
                .getPassword()).isNotEqualTo("1234").isNotBlank();

    }

    @Test
    @DisplayName("중복된 이메일 사용시 회원가입 실패")
    void signUp_duplicateEmail() {
        //given
        Member member1 = Member.builder()
                .name("jeensh")
                .email("jeensh25@gmail.com")
                .password("1234")
                .build();

        memberRepository.save(member1);

        SignUp signUp = SignUp.builder()
                .name("hee")
                .email("jeensh25@gmail.com")
                .password("4321")
                .build();

        //expect
        assertThatThrownBy(() -> authService.signUp(signUp)).isInstanceOf(AlreadyExistsEmailException.class);
    }
}
