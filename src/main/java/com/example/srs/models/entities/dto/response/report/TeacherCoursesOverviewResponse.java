package com.example.srs.models.entities.dto.response.report;

public record TeacherCoursesOverviewResponse(
        Long totalCourses,
        Long totalEnrollments,
        Long totalStudent
) {
}
