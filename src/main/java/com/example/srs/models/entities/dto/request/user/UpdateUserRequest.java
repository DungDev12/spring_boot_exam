package com.example.srs.models.entities.dto.request.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "Email không được để trống")
        @Column(unique = true, nullable = false)
        @Email(message = "Email không hợp lệ")
        String email,
        String firstName,
        String lastName,
        String phone,
        String address
) {
}
