package com.example.srs.repositories;

import com.example.srs.models.entities.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Page<Lesson> findByCourseId(Long courseId, Pageable pageable);

    boolean existsByIdAndCourse_Teacher_Id(Long lessonId, Long id);

    List<Lesson> findByCourseIdAndPublishedTrue(Long courseId);
}
