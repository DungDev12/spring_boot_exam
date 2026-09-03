package com.example.srs.services.impl;

import com.example.srs.models.entities.dto.response.report.StudentProgressResponse;
import com.example.srs.models.entities.dto.response.report.TeacherCoursesOverviewResponse;
import com.example.srs.models.entities.dto.response.report.TopCourseResponse;
import com.example.srs.repositories.CourseRepository;
import com.example.srs.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final CourseRepository courseRepository;
    private final UserServiceImpl userService;

    @Override
    public Page<TopCourseResponse> getTopCourses(Pageable pageable) {
        return courseRepository.findTopCourse(pageable);
    }

    @Override
    public Page<StudentProgressResponse> getStudentProgress(Long studentId,Pageable pageable) {
        return null;
    }

    @Override
    public TeacherCoursesOverviewResponse getTeacherCoursesOverview(Long teacherId) {
        userService.getById(teacherId);
        return courseRepository.findTeacherCoursesOverview(teacherId);
    }
}
