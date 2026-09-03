package com.example.srs.models.entities.dto.request.lesson;

import jakarta.validation.constraints.NotBlank;

public record CreateLessonRequest(
        @NotBlank(message = "Tiêu đề không được để trống")
        String title,
        String content,
        String contentUrl,
        int orderIndex,
        boolean isPublished
) {
}
