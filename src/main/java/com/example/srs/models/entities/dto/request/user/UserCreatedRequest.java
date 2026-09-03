package com.example.srs.models.entities.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
public record UserCreatedRequest(

        @NotBlank(message = "Tài khoản không được để trống")
        String username,

        @Size(min = 8, message = "Mật khẩu ít nhất 8 kí tự")
        @NotBlank(message = "Mật khẩu không được để trống")
        String password,

        @Email(message = "Email không hợp lệ")
        @NotBlank(message = "Email không được để trống")
        String email,

        String firstName,

        @NotBlank(message = "Tên không được dể trống")
        String lastName,
        String address,
        String phone

) {

}
