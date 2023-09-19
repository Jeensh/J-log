package com.jeensh.j_log.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
//@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring()
                        .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .requestMatchers(new AntPathRequestMatcher("/error"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/auth/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/signup")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(
                        configurer -> configurer
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                )
                .userDetailsService(userDetailsService())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails user = User
                .withUsername("jeensh")
                .password("1234")
                .roles("ADMIN")
                .build();
        manager.createUser(user);
        return manager;
    }
}
