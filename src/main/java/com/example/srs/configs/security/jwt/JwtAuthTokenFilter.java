package com.example.srs.configs.security.jwt;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.JwtException;
import com.example.srs.securities.UserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailService userDetailService;
    private final JwtEntryPoint jwtEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        try {
            if (token != null) {
                jwtProvider.validateToken(token);
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e){
            log.warn("JWT expired: {}", e.getMessage());
            exceptionEntryPoint(
                    request,
                    response,
                    "Token hết hạn",
                    ERRORCODE.EXPIRED_JWT_TOKEN,
                    HttpStatus.UNAUTHORIZED);
            return;
        }  catch (InvalidKeyException | SignatureException e) {
            log.warn("JWT key invalid: {}", e.getMessage());
            exceptionEntryPoint(
                    request,
                    response,
                    "Token không hợp lệ",
                    ERRORCODE.INVALID_JWT_TOKEN,
                    HttpStatus.UNAUTHORIZED);
            return;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT Exception: {}", e.getMessage());
            exceptionEntryPoint(
                    request,
                    response,
                    "Token không hợp lệ",
                    ERRORCODE.INVALID_JWT_TOKEN,
                    HttpStatus.UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("Exception in JwtAuthTokenFilter", e);
        }

        filterChain.doFilter(request, response);
    }

    private void exceptionEntryPoint(
            HttpServletRequest request,
            HttpServletResponse response,
            String message,
            ERRORCODE errorcode,
            HttpStatus httpStatus
            ) throws ServletException, IOException {
        JwtException exception =
                new JwtException(
                        message,
                        errorcode,
                        httpStatus
                );
        jwtEntryPoint.commence(
                request,
                response,
                exception
        );
    }

    public String getTokenFromRequest(HttpServletRequest request) {
;        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;

    }
}
