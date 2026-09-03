package com.example.srs.models.entities.dto.request.notification;

import com.example.srs.enums.StatusNotification;

public record NotificationFilterRequest(
        Boolean read,
        StatusNotification type
) {
}
