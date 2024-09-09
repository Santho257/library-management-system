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
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    private final String[] WHITE_LIST = new String[]{"/swagger-ui/**", "/ws","/ws/**","/swagger-ui.html", "/v3/api-docs/**", "/books/list", "/authors/list", "/books/sort", "/authors/sort", "/books/search", "/authors/search", "/auth/**", "/login", "/error"};

    private final String[] BORROWER_LIST = new String[]{"/library/borrow", "/library/return/**", "/library/borrower", "library/borrower/unreturned"};

    private final String[] ADMIN_LIST = new String[]{"/borrowers/**","/borrowers", "/books/**", "/authors/**","/library", "/library/unreturned", "/library/borrower/**","/library/book/**","/library/book",  "/bot/**"};

    private final String[] BOTH_GET_LIST = new String[]{
            "/messages/**", "/bot", "/bot/**","/borrowers/admins/online"
    };

    private final String[] MESSAGE_MAPPINGS = new String[]{
            "/app/**", "/user/**", "/topic/public"
    };

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
        security.cors(cors -> cors.configurationSource(corsConfigurationSource));
        security.csrf(AbstractHttpConfigurer::disable);

        security.authorizeHttpRequests(request ->
                request
                        .requestMatchers(HttpMethod.GET, "/books","/authors","/ws")
                        .permitAll()
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, BOTH_GET_LIST)
                        .hasAnyRole("BORROWER","ADMIN")
                        .requestMatchers(BORROWER_LIST)
                        .hasRole("BORROWER")
                        .requestMatchers(ADMIN_LIST)
                        .hasRole("ADMIN")
                        .requestMatchers(MESSAGE_MAPPINGS)
                        .hasAnyRole("ADMIN", "BORROWER")


        );
        security.sessionManagement(config ->
                config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        security.authenticationProvider(authenticationProvider());
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow credentials (cookies, etc.)
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
