package com.example.srs.repositories;

import com.example.srs.models.entities.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByEnrollment_IdAndLesson_Id(Long enrollmentId, Long lessonId);

    long countByEnrollment_Id(Long enrollmentId);

    long countByEnrollment_IdAndCompletedTrue(Long enrollmentId);
}
