package com.example.srs.models.entities.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewRequest(
        String comment,

        @NotNull(message = "Rating không được để trống")
        @Min(value = 0, message = "Rating phải lớn hơn hoặc bằng 0")
        @Max(value = 5, message = "Rating phải nhỏ hơn hoặc bằng 5")
        int rating
) {
}
