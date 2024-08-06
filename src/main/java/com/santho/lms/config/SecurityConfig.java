package com.santho.lms.config;

import com.santho.lms.filters.JwtAuthenticationFilter;
import com.santho.lms.models.Role;
import com.santho.lms.services.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    private final String[] WHITE_LIST = new String[]{"/swagger-ui/**", "/swagger-ui.html",
            "/v3/api-docs/**", "/books/list", "/authors/list", "/books/sort", "/authors/sort",
            "/books/search", "/authors/search", "/auth/**", "/login", "/error"};

    private final String[] BORROWER_LIST = new String[]{"/library/borrow", "/library/return/**"};

    private final String[] ADMIN_LIST = new String[]{"/borrower/**", "/books/**", "/authors/**", "/library/unreturned", "/library/borrower/**"};

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.cors(AbstractHttpConfigurer::disable);
        security.csrf(AbstractHttpConfigurer::disable);

        security.authorizeHttpRequests(request ->
                request
                        .requestMatchers(HttpMethod.GET, "/books","/authors")
                        .permitAll()
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .requestMatchers(BORROWER_LIST)
                        .hasRole("BORROWER")
                        .requestMatchers(ADMIN_LIST)
                        .hasRole("ADMIN")
        );
        security.sessionManagement(config ->
                config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        security.authenticationProvider(authenticationProvider());
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }
}
