package com.example.srs.services;

import com.example.srs.models.entities.Lesson;
import com.example.srs.models.entities.dto.request.lesson.CreateLessonRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonPublishRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonRequest;
import com.example.srs.models.entities.dto.response.lesson.LessonInfoResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface LessonService {

    LessonResponse createLesson( Long courseId, CreateLessonRequest request);
    LessonResponse updateLesson(Long courseId, UpdateLessonRequest request);
    Page<LessonResponse> getLessonsByCourse(Long lessonId, Pageable pageable);
    LessonResponse updateLessonPublished(Long lessonId, UpdateLessonPublishRequest request);
    LessonInfoResponse getLessonById(Long id);
    List<Lesson> getPublishLessonByCourseId(Long courseId);
    Lesson getById(Long id);
    void deleteById(Long id);
}
