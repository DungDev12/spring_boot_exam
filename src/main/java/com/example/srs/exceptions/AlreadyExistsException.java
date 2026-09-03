package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistsException extends RuntimeException {
    private final ERRORCODE errorCode;
    public AlreadyExistsException(
            String resourceName,
            String fieldName,
            Object fieldValue,
            ERRORCODE errorCode
    ) {
        super(String.format(
                "%s already exists with %s: %s",
                resourceName,
                fieldName,
                fieldValue
        ));
        this.errorCode = errorCode;
    }

    public AlreadyExistsException(
            String message,
            ERRORCODE errorCode
    ) {
        super(message);
        this.errorCode = errorCode;
    }
}
