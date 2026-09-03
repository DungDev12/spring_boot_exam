package com.example.srs.models.entities.dto.response.report;

public record TopCourseResponse(
        Long courseId,
        String title,
        Long totalEnrollments
) {
}
