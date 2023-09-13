package com.jeensh.j_log.api.config;

import com.jeensh.j_log.api.config.data.MemberSession;
import com.jeensh.j_log.api.domain.Session;
import com.jeensh.j_log.api.exception.Unauthorized;
import com.jeensh.j_log.api.repository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Slf4j
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest == null) {
            log.error("HttpServletRequest null");
            throw new Unauthorized();
        }

        String accessToken = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies == null) throw new Unauthorized();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("SESSION")) {
                accessToken = cookie.getValue();
            }
        }

        if (!StringUtils.hasText(accessToken)) throw new Unauthorized();
        Session session = sessionRepository.findByAccessToken(accessToken).orElseThrow(Unauthorized::new);

        return new MemberSession(session.getMember().getId());
    }
}
