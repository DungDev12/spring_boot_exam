package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import com.example.srs.enums.StatusNotification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Message không được để trống")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StatusNotification type = StatusNotification.NEW_COURSE;

    @Column(length = 500)
    private String targetUrl;

    @Column(
            name = "is_read",
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean read = false;
}
