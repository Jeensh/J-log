package com.jeensh.j_log.api.config;

import com.jeensh.j_log.api.config.data.MemberSession;
import com.jeensh.j_log.api.exception.Unauthorized;
import com.jeensh.j_log.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final ActiveProfile activeProfile;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String jwt = webRequest.getHeader("Authorization");
        if (!StringUtils.hasText(jwt)) {
            throw new Unauthorized();
        }

        try {
            Claims claims = getClaimsFromJwt(jwt, activeProfile.getJwtKey());

            checkTokenExpired(claims);

            String memberId = claims.getSubject();
            return new MemberSession(Long.parseLong(memberId));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }

    /**
     * 토큰 유효기간 만료시 ExpiredJwtException 발생
     */
    private static void checkTokenExpired(Claims claims) {
        claims.getExpiration().before(new Date());
    }

    private static Claims getClaimsFromJwt(String token, byte[] key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
