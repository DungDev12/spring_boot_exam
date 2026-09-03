package com.example.srs.models.entities.dto.response.notification;

import com.example.srs.enums.StatusNotification;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        StatusNotification type,
        String message,
        String targetUrl,
        boolean read,
        LocalDateTime createdAt
) {
}
