package com.example.srs.models.entities.dto.request.enrollment;

import com.example.srs.enums.StatusEnrollments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnrollmentFilterRequest(
        Long studentId,

        Long courseId,

        StatusEnrollments status,

        BigDecimal minProgress,

        BigDecimal maxProgress,

        LocalDateTime enrollmentFrom,

        LocalDateTime enrollmentTo
) {
}
