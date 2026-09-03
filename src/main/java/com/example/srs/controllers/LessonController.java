package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.lesson.UpdateLessonPublishRequest;
import com.example.srs.models.entities.dto.request.lesson.UpdateLessonRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonInfoResponse;
import com.example.srs.models.entities.dto.response.lesson.LessonResponse;
import com.example.srs.services.impl.LessonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonServiceImpl lessonService;


    @GetMapping("/{lessonId}")
    public ResponseEntity<ApiResponse<LessonInfoResponse>> getLessonById(
            @PathVariable Long lessonId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        lessonService.getLessonById(lessonId),
                        "Lấy chi tiết bài học theo ID: " +lessonId+ " thành công"));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLessonById(
            @PathVariable Long lessonId,
            @Valid @RequestBody UpdateLessonRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        lessonService.updateLesson(lessonId,request),
                        "Cập nhật bài học theo ID: " +lessonId+ " thành công"
                ));
    }

    @PutMapping("/{lessonId}/publish")
    public ResponseEntity<ApiResponse<LessonResponse>> updateLessonPublishedById(
            @PathVariable Long lessonId,
            @RequestBody UpdateLessonPublishRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        lessonService.updateLessonPublished(lessonId,request),
                        "Cập nhật published bài học theo ID: " +lessonId+ " thành công"
                ));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<?> deleteLessonById(
            @PathVariable Long lessonId
    ){
        lessonService.deleteById(lessonId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
