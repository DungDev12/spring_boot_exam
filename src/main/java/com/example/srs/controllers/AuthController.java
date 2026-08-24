package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.UserLoginRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest dto){
        return new ResponseEntity<>(ApiResponse
                .success(authService.login(dto),"Đăng nhập thành công"), HttpStatus.OK);
    }
}
