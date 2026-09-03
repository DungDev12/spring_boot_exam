package com.example.srs.repositories;

import com.example.srs.models.entities.Notification;
import com.example.srs.models.entities.dto.request.notification.NotificationFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
        SELECT n 
        FROM Notification n 
        WHERE n.user.id = :userId
        AND (
                :#{#filter.read} IS NULL 
                 OR n.read = :#{#filter.read}
                )
        AND (
                :#{#filter.type} IS NULL 
                  OR n.type = :#{#filter.type}
                )
        """)
    Page<Notification> findAllByIdAndFilter(
            @Param("userId") Long userId,
            @Param("filter") NotificationFilterRequest filter,
            Pageable pageable
    );
}
