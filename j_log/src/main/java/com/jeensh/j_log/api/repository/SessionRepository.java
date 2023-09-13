package com.jeensh.j_log.api.repository;

import com.jeensh.j_log.api.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);
}
