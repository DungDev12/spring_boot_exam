package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.enrollment.CreateEnrollmentRequest;
import com.example.srs.models.entities.dto.request.enrollment.EnrollmentFilterRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.enrollment.DetailEnrollmentResponse;
import com.example.srs.models.entities.dto.response.enrollment.EnrollmentResponse;
import com.example.srs.services.impl.EnrollmentServiceImpl;
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
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentServiceImpl enrollmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAll(
            @ModelAttribute EnrollmentFilterRequest filterRequest,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )Pageable pageable
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.pageSuccess(
                        enrollmentService.getAll(filterRequest,pageable),
                        "Lấy dữ liệu thành công"
                ));
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<ApiResponse<DetailEnrollmentResponse>> getDetailEnrollmentById(
            @PathVariable Long enrollmentId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        enrollmentService.getDetailEnrollmentById(enrollmentId),
                        "Lấy khoá học thành công"
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DetailEnrollmentResponse>> createEnrollment(
            @Valid @RequestBody CreateEnrollmentRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        enrollmentService.createEnrollment(request),
                        "Đăng ký khoá học thành công"));
    }

    @PutMapping("/{enrollmentId}/lessons/{lessonId}/complete")
    public ResponseEntity<ApiResponse<DetailEnrollmentResponse>> completeLesson(
            @PathVariable Long enrollmentId,
            @PathVariable Long lessonId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        enrollmentService.updateCompleteLesson(enrollmentId,lessonId),
                        "Cập nhật trạng thành công"
                ));
    }
}
