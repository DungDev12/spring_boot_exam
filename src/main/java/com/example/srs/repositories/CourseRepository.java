package com.example.srs.repositories;

import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.dto.request.course.CourseFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
    SELECT c
    FROM Course c
    WHERE (
        :#{#filter.search} IS NULL
        OR :#{#filter.search} = ''
        OR LOWER(c.title) LIKE LOWER(CONCAT('%', :#{#filter.search}, '%'))
        OR LOWER(c.description) LIKE LOWER(CONCAT('%', :#{#filter.search}, '%'))
    )
    AND (
        :#{#filter.teacherId} IS NULL
        OR c.teacher.id = :#{#filter.teacherId}
    )
    AND (
        :#{#filter.status} IS NULL
         OR c.status = :#{#filter.status}
        )
    """)
    Page<Course> findAllAndFilter(
            @Param("filter") CourseFilterRequest filter,
            Pageable pageable
    );

    boolean existsByIdAndTeacherId(
            Long courseId,
            Long teacherId
    );
}
