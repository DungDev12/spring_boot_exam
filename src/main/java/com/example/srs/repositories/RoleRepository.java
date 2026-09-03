package com.example.srs.repositories;

import com.example.srs.models.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("""
        SELECT r
        FROM Role r
        WHERE (
          :search IS NULL
          OR :search = ''
          OR LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<Role> findAllSearchName(
            @Param("search") String search,
            Pageable pageable
    );

    boolean existsByName(String name);
}
