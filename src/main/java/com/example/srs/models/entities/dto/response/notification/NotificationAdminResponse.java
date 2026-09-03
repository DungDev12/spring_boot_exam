package com.example.srs.models.entities.dto.response.notification;

import com.example.srs.enums.StatusNotification;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;

import java.time.LocalDateTime;

public record NotificationAdminResponse(
        Long id,
        StatusNotification type,
        String message,
        String targetUrl,
        UserInfoResponse user,
        boolean read,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
