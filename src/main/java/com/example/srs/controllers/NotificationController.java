package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.notification.CreateNotificationRequest;
import com.example.srs.models.entities.dto.request.notification.NotificationFilterRequest;
import com.example.srs.models.entities.dto.request.notification.UpdateReadNotificationRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.notification.NotificationAdminResponse;
import com.example.srs.models.entities.dto.response.notification.NotificationResponse;
import com.example.srs.services.impl.NotificationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getAllAndFilter(
            @ModelAttribute NotificationFilterRequest filter,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.pageSuccess(notificationService.getAllAndFilter(filter,pageable),
                        "Lấy danh sách thành công"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationAdminResponse>> createNotification(
            @Valid @RequestBody CreateNotificationRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        notificationService.createNotification(request),
                        "Tạo thông báo thành công"
                ));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long notificationId
    ){
        notificationService.deleteById(notificationId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> updateReadNotificationById(
            @PathVariable Long notificationId,
            @Valid @RequestBody UpdateReadNotificationRequest request
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        notificationService.updateReadNotificationById(notificationId,request),
                        "Cập nhật thành công"
                ));
    }
}
