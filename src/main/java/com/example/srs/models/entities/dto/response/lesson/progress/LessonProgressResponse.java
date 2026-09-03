package com.example.srs.models.entities.dto.response.lesson.progress;

import java.time.LocalDateTime;

public record LessonProgressResponse(
        Long id,
        LocalDateTime lastAccessedAt,
        LocalDateTime completedAt,
        LocalDateTime updatedAt,
        boolean completed) {
}
