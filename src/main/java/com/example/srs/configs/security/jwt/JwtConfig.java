package com.example.srs.configs.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean
    public SecretKey jwtSecretKey(JwtProperties properties){
        if (properties.secret() == null ||
                properties.secret().isBlank()) {

            throw new IllegalStateException(
                    "JWT_SECRET không được để trống"
            );
        }

        byte[] keyBytes = properties.secret()
                .getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET phải có ít nhất 256 bits (32 bytes)"
            );
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
