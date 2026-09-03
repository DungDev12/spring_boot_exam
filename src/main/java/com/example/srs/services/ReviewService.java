package com.example.srs.services;

import com.example.srs.models.entities.Review;
import com.example.srs.models.entities.dto.request.review.CreateReviewRequest;
import com.example.srs.models.entities.dto.request.review.ReviewFilterRequest;
import com.example.srs.models.entities.dto.request.review.UpdateReviewRequest;
import com.example.srs.models.entities.dto.response.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse createReview(Long courseId, CreateReviewRequest request);

    Page<ReviewResponse> getAllAndSearch(Long courseId, ReviewFilterRequest filter, Pageable pageable);

    ReviewResponse updateReview(Long id, UpdateReviewRequest request);

    Review getById(Long id);

    void deleteById(Long id);
}
