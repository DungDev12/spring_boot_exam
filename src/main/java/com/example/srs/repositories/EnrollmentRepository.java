package com.example.srs.repositories;

import com.example.srs.models.entities.Enrollment;
import com.example.srs.models.entities.dto.request.enrollment.EnrollmentFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);

    @Query("""
        SELECT e
        FROM Enrollment e
        WHERE (
                :#{#filter.status} IS NULL
                        OR e.status = :#{#filter.status}
                )
        AND (
                :#{#filter.courseId} IS NULL
                        OR e.course.id = :#{#filter.courseId}
                )
        AND (
                :#{#filter.minProgress} IS NULL
                        OR e.progressPercentage >= :#{#filter.minProgress}
                )
        AND (
                :#{#filter.maxProgress} IS NULL
                        OR e.progressPercentage <= :#{#filter.maxProgress}
                )
        AND (
                :#{#filter.enrollmentFrom} IS NULL 
                        OR e.enrollmentDate >= :#{#filter.enrollmentFrom}
                )
        AND (
                :#{#filter.enrollmentTo} IS NULL 
                        OR e.enrollmentDate <= :#{#filter.enrollmentTo}
                )
        AND e.student.id = :userId
        """)
    Page<Enrollment> findAllEnrollments(
            @Param("userId") Long userId,
            @Param("filter") EnrollmentFilterRequest filter,
            Pageable pageable
    );

    Optional<Enrollment> findByIdAndStudent_Id(Long id, Long studentId);

}
