package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.user.UserLoginRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.models.entities.dto.response.user.UserLoginResponse;
import com.example.srs.securities.UserPrinciple;
import com.example.srs.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(@Valid @RequestBody UserLoginRequest dto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(authService.login(dto),"Đăng nhập thành công")
                );
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Map<String,Boolean>>> verifyToken(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        Map.of(
                                "valid", true
                        ),
                        "Token hợp lệ")
                );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getInfoMe(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(authService.getCurrentUser(),
                                "Lấy dữ liệu thành công")
                );
    }
}
