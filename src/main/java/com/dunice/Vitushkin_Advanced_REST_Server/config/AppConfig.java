package com.dunice.Vitushkin_Advanced_REST_Server.config;

import com.dunice.Vitushkin_Advanced_REST_Server.logging.LoggerInterceptor;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {
    private final UserRepository userRepository;
    private final LoggerInterceptor loggerInterceptor;

    @Bean
    public UserDetailsService userDetailsService() {
        return id -> userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
    }
}
