package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
public class JwtException extends AuthenticationException {
    private HttpStatus httpStatus;
    private final ERRORCODE errorCode;
    public JwtException(String message, ERRORCODE errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
