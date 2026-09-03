package com.example.srs.models.entities.dto.response.course;

import com.example.srs.enums.StatusCourses;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;

import java.math.BigDecimal;

public record CourseResponse(
        Long id,
        String title,
        String description,
        UserInfoResponse teacher,
        BigDecimal price,
        int durationHours,
        StatusCourses status
) {
}
