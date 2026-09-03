package com.example.srs.models.mapper;

import com.example.srs.models.entities.Notification;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.notification.CreateNotificationRequest;
import com.example.srs.models.entities.dto.response.notification.NotificationAdminResponse;
import com.example.srs.models.entities.dto.response.notification.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "user", ignore = true)
    Notification toNotification(CreateNotificationRequest request);

    NotificationResponse toResponse(Notification notification);
    NotificationAdminResponse toAdminResponse(Notification notification);
    default String map(Role role){
        return role != null ? role.getName() : null;
    }
}
