package com.example.srs.models.entities.dto.response;


import com.example.srs.enums.ERRORCODE;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse<T> {
    private boolean success;
    private ERRORCODE errorCode;
    private String message;
    private T data;
    private LocalDateTime time;

    public static <T> ErrorResponse<?> error(boolean success,ERRORCODE errorCode, String message){
        return ErrorResponse.builder()
                .success(success)
                .errorCode(errorCode)
                .message(message)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> ErrorResponse<?> errorValidation(T data){
        return ErrorResponse.builder()
                .success(false)
                .errorCode(ERRORCODE.ERROR_VALIDATION)
                .data(data)
                .build();
    }
}
