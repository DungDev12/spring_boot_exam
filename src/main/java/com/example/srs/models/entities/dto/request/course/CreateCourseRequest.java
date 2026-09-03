package com.example.srs.models.entities.dto.request.course;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateCourseRequest(
        @NotBlank(message = "Tiêu đề không được để trống")
        String title,

        String description,

        @NotNull(message = "Giáo viên không được để trống")
        Long teacherId,

        @NotNull(message = "Giá khoá học không được để trống")
        @DecimalMin(
                value = "0.00",
                inclusive = false,
                message = "Giá khóa học phải lớn hơn 0"
        )
        BigDecimal price,

        @PositiveOrZero(message = "Số giờ không được bé hơn 0")
        int durationHours
) {
}
