package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private final ERRORCODE errorCode;
    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Object fieldValue,
            ERRORCODE errorCode
    ) {
        super(String.format(
                "%s not found with %s: %s",
                resourceName,
                fieldName,
                fieldValue
        ));
        this.errorCode = errorCode;
    }
    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            ERRORCODE errorCode
    ) {
        super(String.format(
                "%s not found with %s",
                resourceName,
                fieldName
        ));
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(String message, ERRORCODE errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
