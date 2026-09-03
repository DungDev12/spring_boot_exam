package com.example.srs.models.entities.dto.request.enrollment;

import jakarta.validation.constraints.NotNull;

public record CreateEnrollmentRequest(

        @NotNull(message = "Khoá học không được để trống")
        Long courseId
) {
}
