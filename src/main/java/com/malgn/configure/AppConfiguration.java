package com.malgn.configure;

import com.malgn.entity.Member;
import com.malgn.entity.Role;
import com.malgn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AppConfiguration {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initMembers() {
        return args -> {
            if (memberRepository.findByName("admin").isEmpty()) {
                memberRepository.save(Member.builder()
                        .name("admin")
                        .password(passwordEncoder.encode("admin1234@"))
                        .role(Role.ADMIN)
                        .build());
            }
        };
    }
}
