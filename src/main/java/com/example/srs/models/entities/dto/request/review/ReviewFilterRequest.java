package com.example.srs.models.entities.dto.request.review;

public record ReviewFilterRequest(
        Integer ratingMin,
        Integer ratingMax
) {
}
