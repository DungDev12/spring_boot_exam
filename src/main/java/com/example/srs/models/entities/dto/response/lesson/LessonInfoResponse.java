package com.example.srs.models.entities.dto.response.lesson;

import java.time.LocalDateTime;

public record LessonInfoResponse(
        Long id,
        String content,
        String contentUrl,
        boolean isPublished,
        int orderIndex,
        LessonSummaryResponse course,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
