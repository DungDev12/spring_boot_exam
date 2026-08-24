package com.example.srs.models.entities.dto.response;

import com.example.srs.enums.ERRORCODE;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<?> success(T data, String message){
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }
}
