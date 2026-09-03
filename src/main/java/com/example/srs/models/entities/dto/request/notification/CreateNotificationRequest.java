package com.example.srs.models.entities.dto.request.notification;

import com.example.srs.enums.StatusNotification;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationRequest(
        @NotNull(message = "User không được để trống")
        Long userId,
        StatusNotification type,
        String message,
        String targetUrl
) {
}
