package com.example.srs.models.entities.dto.request.course;

import com.example.srs.enums.StatusCourses;

public record CourseFilterRequest(
        String search,
        Long teacherId,
        StatusCourses status
) {
}
