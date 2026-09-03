package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.review.UpdateReviewRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.review.ReviewResponse;
import com.example.srs.services.impl.ReviewServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReviewById(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        reviewService.updateReview(reviewId, request),
                        "Cập nhật thành công"
                ));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long reviewId
    ){
        reviewService.deleteById(reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
