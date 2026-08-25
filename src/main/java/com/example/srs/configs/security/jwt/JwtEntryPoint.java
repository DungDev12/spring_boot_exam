package com.example.srs.configs.security.jwt;

import com.example.srs.enums.ERRORCODE;
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
        log.warn(
                "Authentication failed: {} {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                authException.getMessage()
        );

        ErrorResponse<?> errorResponse = ErrorResponse.error(false, ERRORCODE.UNAUTHORIZED,"Authentication is required");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        extracted(response);
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