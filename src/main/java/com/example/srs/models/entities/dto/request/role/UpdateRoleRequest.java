package com.example.srs.models.entities.dto.request.role;

import jakarta.validation.constraints.Pattern;

public record UpdateRoleRequest(
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "Chỉ được phép nhập chữ cái và không khoảng cách"
        )
        String name,
        String description
) {
}
