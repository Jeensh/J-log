package com.jeensh.j_log.api.config;

import com.jeensh.j_log.api.config.data.MemberSession;
import com.jeensh.j_log.api.domain.Session;
import com.jeensh.j_log.api.exception.Unauthorized;
import com.jeensh.j_log.api.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader("Authorization");
        if(!StringUtils.hasText(accessToken)) throw new Unauthorized();

        Session session = sessionRepository.findByAccessToken(accessToken).orElseThrow(Unauthorized::new);


        return new MemberSession(session.getMember().getId());
    }
}
