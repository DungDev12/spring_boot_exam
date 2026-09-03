package com.example.srs.services;

import com.example.srs.models.entities.Enrollment;
import com.example.srs.models.entities.dto.request.enrollment.CreateEnrollmentRequest;
import com.example.srs.models.entities.dto.request.enrollment.EnrollmentFilterRequest;
import com.example.srs.models.entities.dto.response.enrollment.DetailEnrollmentResponse;
import com.example.srs.models.entities.dto.response.enrollment.EnrollmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EnrollmentService {
    DetailEnrollmentResponse createEnrollment(CreateEnrollmentRequest courseId);
    Page<EnrollmentResponse> getAll(EnrollmentFilterRequest request, Pageable pageable);
    DetailEnrollmentResponse getDetailEnrollmentById(Long id);
    Enrollment getById(Long id);
    DetailEnrollmentResponse updateCompleteLesson(Long enrollmentId, Long LessonId);
}
