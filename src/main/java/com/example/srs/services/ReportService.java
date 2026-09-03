package com.example.srs.services;

import com.example.srs.models.entities.dto.response.report.StudentProgressResponse;
import com.example.srs.models.entities.dto.response.report.TeacherCoursesOverviewResponse;
import com.example.srs.models.entities.dto.response.report.TopCourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReportService {

    Page<TopCourseResponse> getTopCourses(Pageable pageable);

    Page<StudentProgressResponse> getStudentProgress(Long studentId, Pageable pageable);

    TeacherCoursesOverviewResponse getTeacherCoursesOverview(Long teacherId);
}
