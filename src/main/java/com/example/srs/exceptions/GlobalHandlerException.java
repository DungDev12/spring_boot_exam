package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ErrorResponse> buildResponse(ERRORCODE errorCode, String message, HttpStatus status){
        return ResponseEntity.status(status).body(ErrorResponse.error(false,errorCode,message, status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
       List<Map<String,String>> errors = e.getBindingResult().getFieldErrors().stream()
               .map(fieldError -> {
                   Map<String, String > error = new HashMap<>();
                   error.put("field", fieldError.getField());
                   error.put("message",fieldError.getDefaultMessage());
                   return error;
               }).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.errorValidation(errors));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return buildResponse(ERRORCODE.USER_ALREADY_EXISTS,e.getMessage(),HttpStatus.CONFLICT);
    }
}
