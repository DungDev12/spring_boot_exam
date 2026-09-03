package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Lesson;
import com.example.srs.models.entities.dto.request.lesson.CreateLessonRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonPublishRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonRequest;
import com.example.srs.models.entities.dto.response.lesson.LessonInfoResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonResponse;
import com.example.srs.models.mapper.LessonMapper;
import com.example.srs.repositories.LessonRepository;
import com.example.srs.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseServiceImpl courseService;

    @Override
    @PreAuthorize("""
        hasRole('ADMIN') or
            (
                hasRole('TEACHER') and
                @coursePermission.isOwner(#courseId)
            )
    """)
    public LessonResponse createLesson(
            Long courseId,
            CreateLessonRequest request
    ){
        Lesson lesson = lessonMapper.toLesson(request);
        lesson.setCourse(courseService.getById(courseId));
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }

    @Override
    @PreAuthorize("""
        hasRole('ADMIN') or
            (
                hasRole('TEACHER') and
                @coursePermission.isLessonOwner(#lessonId)
            )
    """)
    public LessonResponse updateLesson(Long lessonId, UpdateLessonRequest request) {
        Lesson lesson = getById(lessonId);
        lessonMapper.updateLessonFromDto(request, lesson);
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }

    @Override
    public Page<LessonResponse> getLessonsByCourse(Long courseId, Pageable pageable) {
        courseService.getById(courseId);
        return lessonRepository.findByCourseId(courseId, pageable)
                .map(lessonMapper::toLessonResponse);
    }

    @Override
    @PreAuthorize("""
        hasRole('ADMIN') or
            (
                hasRole('TEACHER') and
                @coursePermission.isLessonOwner(#lessonId)
            )
    """)
    public LessonResponse updateLessonPublished(Long lessonId, UpdateLessonPublishRequest request) {
        Lesson lesson = getById(lessonId);
        lesson.setPublished(request.isPublished());
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }

    @Override
    public LessonInfoResponse getLessonById(Long id) {
        return lessonMapper.toLessonInfoResponse(getById(id));
    }

    @Override
    public List<Lesson> getPublishLessonByCourseId(Long courseId) {
        return lessonRepository.findByCourseIdAndPublishedTrue(courseId);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id, ERRORCODE.LESSON_NOTFOUND));
    }

    @Override
    public void deleteById(Long id){
        Lesson lesson = getById(id);
        lessonRepository.delete(lesson);
    }
}
