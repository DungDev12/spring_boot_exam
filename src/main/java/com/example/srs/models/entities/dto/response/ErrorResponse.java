package com.example.srs.models.entities.dto.response;


import com.example.srs.enums.ERRORCODE;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse<T> {
    private boolean success;
    private int statusCode;
    private ERRORCODE errorCode;
    private String message;
    private T errors;
    private LocalDateTime timestamp;

    public static <T> ErrorResponse<?> error(boolean success,ERRORCODE errorCode, String message, int statusCode){
        return ErrorResponse.builder()
                .success(success)
                .statusCode(statusCode)
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ErrorResponse<?> errorValidation(T data){
        return ErrorResponse.builder()
                .success(false)
                .statusCode(400)
                .errorCode(ERRORCODE.INVALID_INPUT_DATA)
                .message("Dữ liệu đầu vào không hợp lệ")
                .errors(data)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
