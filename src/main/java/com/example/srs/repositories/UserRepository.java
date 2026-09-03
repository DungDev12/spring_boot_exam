package com.example.srs.repositories;

import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.user.UserFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("""
         SELECT DISTINCT u
         FROM User u
         LEFT JOIN u.roles r
         WHERE (:#{#filter.role} IS NULL OR r.name = :#{#filter.role})
         OR (:#{#filter.status} IS NULL OR u.isActive = :#{#filter.status})
        """)
    Page<User> findAllUsers(
            @Param("filter")UserFilterRequest filter,
            Pageable pageable
    );

    boolean existsByUsername(String username);
    boolean existsByEmail(String mail);
}
