package com.example.srs.models.entities.dto.response.lesson;

import java.time.LocalDateTime;

public record LessonResponse(
        String title,
        String content,
        String contentUrl,
        boolean isPublished,
        int orderIndex,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {
}
