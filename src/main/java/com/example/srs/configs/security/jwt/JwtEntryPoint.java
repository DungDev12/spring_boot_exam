package com.example.srs.configs.security.jwt;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.JwtException;
import com.example.srs.models.entities.dto.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ERRORCODE errorCode = ERRORCODE.UNAUTHORIZED;
        String message = "Authentication is required";

        if (authException instanceof JwtException jwtException) {

            errorCode = jwtException.getErrorCode();
            message = jwtException.getMessage();
        }
        log.warn("Authentication failed: {} {} from {} | User-Agent: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent"));

        ErrorResponse<?> errorResponse =
                ErrorResponse.error(
                        false,
                        errorCode,
                        message,
                        HttpStatus.UNAUTHORIZED.value()
                );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        objectMapper.writeValue(
                response.getWriter(),
                errorResponse
        );
    }

    private static void extracted(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }

}