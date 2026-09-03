package com.example.srs.models.entities.dto.response.enrollment;


import com.example.srs.enums.StatusEnrollments;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record EnrollmentResponse(
        Long id,
        StatusEnrollments status,
        BigDecimal progressPercentage,
        LocalDateTime enrollmentDate,
        LocalDateTime completionDate
) {
}
