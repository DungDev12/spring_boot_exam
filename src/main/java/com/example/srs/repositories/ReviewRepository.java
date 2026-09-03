package com.example.srs.repositories;

import com.example.srs.models.entities.Review;
import com.example.srs.models.entities.dto.request.review.ReviewFilterRequest;
import com.example.srs.models.entities.dto.response.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);


    @Query("""
        SELECT r 
        FROM Review r 
        WHERE r.course.id = :courseId
        AND (
                :#{#filter.ratingMin} IS NULL 
                        OR r.rating >= :#{#filter.ratingMin}
                )
         AND (
                :#{#filter.ratingMax} IS NULL 
                        OR r.rating <= :#{#filter.ratingMax}
                )
        """)
    Page<Review> findReviewByCourseId(
            @Param("courseId") Long courseId,
            @Param("filter") ReviewFilterRequest filter,
            Pageable pageable
    );
}
