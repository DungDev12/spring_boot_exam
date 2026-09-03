package com.example.srs.models.entities.dto.response.lesson;


import com.example.srs.models.entities.dto.response.user.UserInfoResponse;

public record LessonSummaryResponse(
        Long id,
        String title,
        String description,
        UserInfoResponse teacher
) {
}
