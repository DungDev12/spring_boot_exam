package com.example.srs.models.entities.dto.response;

import com.example.srs.enums.ERRORCODE;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private PageMeta page;

    public static <T> ApiResponse<T> success(String message){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<List<T>> pageSuccess(Page<T> data, String message){
        String finalMessage = data.isEmpty()
                ? "Không có dữ liệu"
                : message;
        return ApiResponse.<List<T>>builder()
                .success(true)
                .message(finalMessage)
                .data(data.getContent())
                .page(PageMeta.from(data))
                .build();
    }
}
