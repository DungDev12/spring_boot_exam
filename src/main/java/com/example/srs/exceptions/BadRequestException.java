package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
    private final ERRORCODE errorCode;
    public BadRequestException(String message, ERRORCODE errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
