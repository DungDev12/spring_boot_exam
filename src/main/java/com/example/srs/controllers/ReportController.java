package com.example.srs.controllers;

import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.report.TeacherCoursesOverviewResponse;
import com.example.srs.models.entities.dto.response.report.TopCourseResponse;
import com.example.srs.services.impl.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;


    @GetMapping("/top-courses")
    public ResponseEntity<ApiResponse<List<TopCourseResponse>>> getTopCourse(
            @PageableDefault(
                    page = 0,
                    size = 10
            )Pageable pageable
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.pageSuccess(
                        reportService.getTopCourses(pageable),
                        "Lấy danh sách các khoá học phổ thông thành công"
                ));
    }

    @GetMapping("/teacher-courses-overview/{teacherId}")
    public ResponseEntity<ApiResponse<TeacherCoursesOverviewResponse>> getTeacherCoursesOverviewById(
            @PathVariable Long teacherId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        reportService.getTeacherCoursesOverview(teacherId),
                        "Lấy thống kê tổng quan về các khóa học của một giảng viên thành công"
                ));
    }
}
