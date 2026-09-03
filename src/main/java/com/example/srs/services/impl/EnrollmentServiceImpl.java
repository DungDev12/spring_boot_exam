package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.enums.StatusEnrollments;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.*;
import com.example.srs.models.entities.dto.request.enrollment.CreateEnrollmentRequest;
import com.example.srs.models.entities.dto.request.enrollment.EnrollmentFilterRequest;
import com.example.srs.models.entities.dto.response.enrollment.DetailEnrollmentResponse;
import com.example.srs.models.entities.dto.response.enrollment.EnrollmentResponse;
import com.example.srs.models.mapper.CourseMapper;
import com.example.srs.models.mapper.EnrollmentMapper;
import com.example.srs.models.mapper.LessonMapper;
import com.example.srs.models.mapper.LessonProgressMapper;
import com.example.srs.repositories.EnrollmentRepository;
import com.example.srs.repositories.LessonProgressRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.services.EnrollmentService;
import com.example.srs.validations.EnrollmentValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentValidator enrollmentValidator;
    private final EnrollmentMapper enrollmentMapper;

    private final CourseServiceImpl courseService;
    private final CourseMapper courseMapper;

    private final CurrentUserService currentUserService;
    private final UserServiceImpl userService;

    private final LessonServiceImpl lessonService;

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonProgressMapper lessonProgressMapper;

    @Override
    @Transactional
    public DetailEnrollmentResponse createEnrollment(CreateEnrollmentRequest request) {
        Course course = courseService.getById(request.courseId());
        User student = userService.getById(currentUserService.getCurrentUserId());
        enrollmentValidator.validateForCreate(course,student);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        List<Lesson> lessons = lessonService.getPublishLessonByCourseId(course.getId());

        List<LessonProgress> progresses =
                lessons.stream()
                        .map(lesson -> {

                            LessonProgress progress =
                                    new LessonProgress();

                            progress.setEnrollment(savedEnrollment);
                            progress.setLesson(lesson);

                            return progress;
                        })
                        .toList();

        lessonProgressRepository.saveAll(progresses);

        return enrollmentMapper.toDetailResponse(savedEnrollment);
    }

    @Override
    public Page<EnrollmentResponse> getAll(EnrollmentFilterRequest filter, Pageable pageable) {
        return enrollmentRepository.findAllEnrollments(currentUserService.getCurrentUserId(),filter, pageable)
                .map(enrollmentMapper::toResponse);
    }

    @Override
    public DetailEnrollmentResponse getDetailEnrollmentById(Long id) {
        Enrollment enrollment = findByIdWithStudent(id);
        return enrollmentMapper.toDetailResponse(enrollment);
    }

    @Override
    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment" , "id", id, ERRORCODE.ENROLLMENT_NOTFOUND));
    }

    private Enrollment findByIdWithStudent(Long id){
        return enrollmentRepository.findByIdAndStudent_Id(
                id,
                currentUserService.getCurrentUserId()
        ).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin đăng ký", ERRORCODE.ENROLLMENT_NOTFOUND));
    }

    @Override
    @Transactional
    public DetailEnrollmentResponse updateCompleteLesson(Long enrollmentId, Long lessonId) {
        Enrollment enrollment = findByIdWithStudent(enrollmentId);
        LessonProgress lessonProgress = enrollmentValidator.validateForUpdateCompleteLesson(enrollment, lessonId);
        lessonProgress.setCompleted(true);
        lessonProgress.setCompletedAt(LocalDateTime.now());
        lessonProgressRepository.save(lessonProgress);

        double totalLessons = lessonProgressRepository.countByEnrollment_Id((enrollmentId));
        double completedLessons = lessonProgressRepository.countByEnrollment_IdAndCompletedTrue(enrollmentId);

        BigDecimal progressPercentage = BigDecimal.valueOf((completedLessons / totalLessons) * 100)
                .setScale(2, RoundingMode.HALF_EVEN);

        enrollment.setProgressPercentage(progressPercentage);

        if(completedLessons == totalLessons){
            enrollment.setStatus(StatusEnrollments.COMPLETED);
            enrollment.setCompletionDate(LocalDateTime.now());
        }

        enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDetailResponse(enrollment);
    }
}
