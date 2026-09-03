package com.example.srs.models.entities.dto.request.course;

import com.example.srs.enums.StatusCourses;
import jakarta.validation.constraints.NotNull;

public record UpdateCourseStatusRequest(
        @NotNull(message = "Trạng thái không được để trống")
        StatusCourses status
) {
}
