package com.example.srs.models.entities.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordUserRequest(
        @NotBlank(message = "Mật khẩu không được để trống")
        String password
) {
}
