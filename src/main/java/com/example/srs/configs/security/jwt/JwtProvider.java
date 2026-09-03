package com.example.srs.configs.security.jwt;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.securities.UserPrinciple;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expired;

    public String generateToken(UserPrinciple userPrinciple){
        Date dateExpiration = new Date(new Date().getTime() + expired * 1000);
        SecretKey key= Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(userPrinciple.getUsername())
                .claim("roles", userPrinciple.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).toList())
                .issuedAt(new Date())
                .expiration(dateExpiration)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                secretKey.getBytes(StandardCharsets.UTF_8)
                        )
                )
                .build()
                .parseSignedClaims(token);
    }

    public String getJti(String token){
        Claims claims = parseToken(token);
        return claims.getId();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(
                        secretKey.getBytes(StandardCharsets.UTF_8)
                ))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims parseToken(String token){
        return  Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(
                        secretKey.getBytes(StandardCharsets.UTF_8)
                ))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
