package com.example.srs.models.entities.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateRoleRequest(

        @NotBlank(message = "Vai trò không được để trống")
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "Chỉ được phép nhập chữ cái và không khoảng cách"
        )
        String name,
        String description
) {
}
