package com.example.srs.services;

import com.example.srs.models.entities.Notification;
import com.example.srs.models.entities.dto.request.notification.CreateNotificationRequest;
import com.example.srs.models.entities.dto.request.notification.NotificationFilterRequest;
import com.example.srs.models.entities.dto.request.notification.UpdateReadNotificationRequest;
import com.example.srs.models.entities.dto.response.notification.NotificationAdminResponse;
import com.example.srs.models.entities.dto.response.notification.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NotificationService {

    NotificationAdminResponse createNotification(CreateNotificationRequest request);
    Page<NotificationResponse> getAllAndFilter(NotificationFilterRequest filter, Pageable pageable);

    NotificationResponse updateReadNotificationById(Long id, UpdateReadNotificationRequest request);

    Notification getById(Long id);

    void deleteById(Long id);
}
