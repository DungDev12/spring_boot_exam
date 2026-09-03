package com.example.srs.exceptions;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    private ResponseEntity<ErrorResponse> buildResponse(ERRORCODE errorCode, String message, HttpStatus status){
        return ResponseEntity.status(status).body(ErrorResponse.error(false,errorCode,message, status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
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
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        log.warn("User already exists: {}", e.getMessage());
        return buildResponse(ERRORCODE.USER_ALREADY_EXISTS,e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException e){
        log.warn(e.getMessage());
        return buildResponse(e.getErrorCode(),e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        return buildResponse(e.getErrorCode(),e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e){
        return buildResponse(e.getErrorCode(), e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(JwtException e){
        return buildResponse(e.getErrorCode(), e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e){
        return buildResponse(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
