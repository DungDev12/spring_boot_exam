package com.example.srs.models.entities.dto.response.review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String comment,
        int rating,
        String studentName,
        LocalDateTime createdAt
) {
}
