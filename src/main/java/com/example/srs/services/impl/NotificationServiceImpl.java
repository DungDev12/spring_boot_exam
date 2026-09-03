package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Notification;
import com.example.srs.models.entities.dto.request.notification.CreateNotificationRequest;
import com.example.srs.models.entities.dto.request.notification.NotificationFilterRequest;
import com.example.srs.models.entities.dto.request.notification.UpdateReadNotificationRequest;
import com.example.srs.models.entities.dto.response.notification.NotificationAdminResponse;
import com.example.srs.models.entities.dto.response.notification.NotificationResponse;
import com.example.srs.models.mapper.NotificationMapper;
import com.example.srs.repositories.NotificationRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.services.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserServiceImpl userService;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public NotificationAdminResponse createNotification(CreateNotificationRequest request) {
        Notification notification = notificationMapper.toNotification(request);
        notification.setUser(userService.getById(request.userId()));
        return notificationMapper.toAdminResponse(notificationRepository.save(notification));
    }

    @Override
    public Page<NotificationResponse> getAllAndFilter(NotificationFilterRequest filter, Pageable pageable) {
        return notificationRepository.findAllByIdAndFilter( currentUserService.getCurrentUserId() ,filter, pageable)
                .map(notificationMapper::toResponse);
    }

    @Override
    public NotificationResponse updateReadNotificationById(Long id, UpdateReadNotificationRequest request) {
        Notification notification = getById(id);
        if(!Objects.equals(notification.getUser().getId(), currentUserService.getCurrentUserId())){
            throw new AccessDeniedException("Bạn không có quyền của thông báo này", ERRORCODE.FORBIDDEN);
        }
        notification.setRead(request.read());
        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id, ERRORCODE.NOTIFICATION_NOTFOUND));
    }

    @Override
    public void deleteById(Long id) {
        Notification notification = getById(id);
        notificationRepository.delete(notification);
    }
}
