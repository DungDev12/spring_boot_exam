package com.example.srs.configs.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record  JwtProperties(
        String secret,
        long expiration
) {
}
