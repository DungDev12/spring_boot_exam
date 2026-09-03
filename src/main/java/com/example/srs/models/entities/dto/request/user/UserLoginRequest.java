package com.example.srs.models.entities.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record UserLoginRequest(
        @NotBlank(message = "Tài khoản không được để trống")
         String username,

        @NotBlank(message = "Mật khẩu không được để trông")
        String password
) {


}
