package com.example.srs.configs.security;

import com.example.srs.configs.security.jwt.JwtAuthTokenFilter;
import com.example.srs.configs.security.jwt.JwtEntryPoint;
import com.example.srs.configs.security.jwt.CustomAccessDenied;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final CustomAccessDenied customAccessDenied;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final JwtEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(auth ->
                        auth.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/login"
                        ).permitAll()

                        // ALL METHOD - ADMIN
                        .requestMatchers(
                                "/api/users/**",
                                "/api/roles/**"
                        ).hasRole("ADMIN")

                        // LESSON - TEACHER / ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/courses/*/lessons"
                        ).hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/lessons/**"
                        ).hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/lessons/**"
                        ).hasAnyRole("TEACHER", "ADMIN")

                        // POST - ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/courses",
                                "/api/notifications"
                        ).hasRole("ADMIN")
                        // PUT - ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/courses/**"
                        ).hasRole("ADMIN")
                        // DELETE - ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/courses/**",
                                "/api/notifications/**"
                        ).hasRole("ADMIN")

                        // ENROLLMENT - STUDENT
                        .requestMatchers(
                                "/api/enrollments",
                                "/api/enrollments/**"
                        ).hasRole("STUDENT")
                        // REVIEW - STUDENT
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/courses/*/reviews"
                        ).hasRole("STUDENT")

                        // AUTHENTICATED USER
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/verify"
                        ).authenticated()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/auth/me"
                        ).authenticated()
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/users/**"
                        ).authenticated()

                        // COURSE / LESSON - AUTH
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/courses",
                                "/api/courses/**",
                                "/api/lessons/**"
                        ).authenticated()

                        // NOTIFICATION - AUTH
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/notifications"
                        ).authenticated()
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/notifications/*/read"
                        ).authenticated()

                        // REVIEW / AUTH - ADMIN
                        .requestMatchers(
                                "/api/reviews/**"
                        ).authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(
                        exception->exception.authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(customAccessDenied))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
