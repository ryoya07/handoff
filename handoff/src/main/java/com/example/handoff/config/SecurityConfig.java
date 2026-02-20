package com.example.handoff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
            	.requestMatchers("/register").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
            	.loginPage("/login")
            	.loginProcessingUrl("/login")
                .defaultSuccessUrl("/handoff", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"));

        return http.build();
    }

    // パスワードエンコーダーのBean定義。BCryptを使用してパスワードをハッシュ化する。
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
