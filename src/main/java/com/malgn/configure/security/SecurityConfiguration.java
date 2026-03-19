package com.malgn.configure.security;

import com.malgn.dto.error.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/members").permitAll()
                        .requestMatchers("/members/**").authenticated()
                        .requestMatchers("/contents", "/contents/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form.successHandler((request, response, authentication) ->
                                writeErrorResponse(response, HttpStatus.OK, "LOGIN_SUCCESS", "로그인에 성공하셨습니다.")
                        ).failureHandler((request, response, exception) ->
                                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "LOGIN_FAILED", "아이디 또는 비밀번호가 올바르지 않습니다.")
                        )
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) ->
                                writeErrorResponse(response, HttpStatus.OK, "LOGOUT_SUCCESS", "로그아웃에 성공하셨습니다."))
                        .permitAll())
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) ->
                                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "MEMBER_UNAUTHORIZED", "로그인이 필요합니다."))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                writeErrorResponse(response, HttpStatus.FORBIDDEN, "CONTENT_ACCESS_DENIED", "접근 권한이 없습니다.")))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    private void writeErrorResponse(HttpServletResponse response,
                                    HttpStatus status,
                                    String code,
                                    String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(ErrorResponse.of(status, code, message))
        );
    }

}
