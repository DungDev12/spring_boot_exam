package com.example.srs.controllers;


import com.example.srs.models.entities.dto.request.course.CourseFilterRequest;
import com.example.srs.models.entities.dto.request.course.CreateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseRequest;
import com.example.srs.models.entities.dto.request.course.UpdateCourseStatusRequest;
import com.example.srs.models.entities.dto.request.lesson.CreateLessonRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.course.CourseResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonResponse;
import com.example.srs.services.impl.CourseServiceImpl;
import com.example.srs.services.impl.LessonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;
    private final LessonServiceImpl lessonService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAll(
            @ModelAttribute CourseFilterRequest filter,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .pageSuccess(courseService.getAll(filter, pageable),
                                "Lấy toàn bộ dữ liệu khoá học thành công")
                );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @RequestBody CreateCourseRequest dto
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        courseService.createCourse(dto),
                        "Tạo khoá học thành công"));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody UpdateCourseRequest dto
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(courseService.updateCourse(courseId,dto),
                                "Cập nhật khoá học "+courseId+" thành công"));
    }

    @PutMapping("/{courseId}/status")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourseStatus(
            @PathVariable Long courseId,
            @Valid @RequestBody UpdateCourseStatusRequest dto
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(courseService.updateCourseStatus(courseId, dto),
                        "Cập nhật trạng thái khoá học "+courseId+" thành công"));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse<String>> deleteById(
            @PathVariable Long courseId
    ){
        courseService.deleteCourseById(courseId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Xoá khoá học " + courseId + " thành công"));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<List<LessonResponse>>> getLessonsByCourseId(
            @PathVariable Long courseId,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "orderIndex",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.pageSuccess(
                        lessonService.getLessonsByCourse(courseId, pageable),
                        "Lấy danh sách của khoá học ID: "+courseId+" thành công"
                ));
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<ApiResponse<LessonResponse>> createLesson(
            @PathVariable Long courseId,
            @Valid @RequestBody CreateLessonRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        lessonService.createLesson(courseId, request),
                        "Thêm danh bài học mới vào khoá học vào ID: "+ courseId +" thành công"
                ));
    }
}
