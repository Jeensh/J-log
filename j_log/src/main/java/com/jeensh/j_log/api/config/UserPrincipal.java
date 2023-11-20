package com.jeensh.j_log.api.config;

import com.jeensh.j_log.api.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long memberId;

    public UserPrincipal(Member member){
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
        this.memberId = member.getId();
    }
}
