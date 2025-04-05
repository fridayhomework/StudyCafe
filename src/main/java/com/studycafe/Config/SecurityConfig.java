package com.studycafe.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/user/signup", "/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 & 회원가입 허용
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")//ADMIN일 경우 admin 이하의 페이지만 접근
                        .requestMatchers("/student/**").hasAnyAuthority("STUDENT") // STUDENT일 경우 student 이하의 페이지만 접근 가능
                        .anyRequest().authenticated() // 그 외에는 로그인 필요
                )
                //로그인 로그아웃 관련 http
                .formLogin(form -> form
                        .loginPage("/user/login")  // 로그인 페이지 경로를 알려줌
                        .loginProcessingUrl("/user/login")//PostMapping 대신요청을 UI 요청을 가로챔
                        .successHandler(loginSuccessHandler)  // 로그인 성공 시 이동할 페이지
                        .failureUrl("/user/login?error") // 로그인 실패 시 이동할 페이지
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // 로그아웃 후 이동할 페이지 -> / : 루트 경로
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화
    }
}
