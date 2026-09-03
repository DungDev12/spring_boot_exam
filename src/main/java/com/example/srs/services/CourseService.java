package com.example.srs.services;

import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.dto.request.course.CourseFilterRequest;
import com.example.srs.models.entities.dto.request.course.CreateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseStatusRequest;
import com.example.srs.models.entities.dto.response.course.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    CourseResponse createCourse(CreateCourseRequest dto);
    CourseResponse updateCourse(Long id, UpdateCourseRequest dto);
    CourseResponse updateCourseStatus(Long id, UpdateCourseStatusRequest dto);
    Page<CourseResponse> getAll(CourseFilterRequest filter, Pageable pageable);
    Course getById(Long id);
    void deleteCourseById(Long id);
}
