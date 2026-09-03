package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.Review;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.review.CreateReviewRequest;
import com.example.srs.models.entities.dto.request.review.ReviewFilterRequest;
import com.example.srs.models.entities.dto.request.review.UpdateReviewRequest;
import com.example.srs.models.entities.dto.response.review.ReviewResponse;
import com.example.srs.models.mapper.ReviewMapper;
import com.example.srs.repositories.ReviewRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.services.ReviewService;
import com.example.srs.validations.ReviewValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewValidator reviewValidator;

    private final CurrentUserService currentUserService;
    private final UserServiceImpl userService;

    private final CourseServiceImpl courseService;

    @Override
    @Transactional
    public ReviewResponse createReview(Long id, CreateReviewRequest request) {
        User user = userService.getById(currentUserService.getCurrentUserId());
        Course course = courseService.getById(id);
        reviewValidator.validateForCreate(course,user);
        Review review = reviewMapper.toReview(request);
        review.setCourse(course);
        review.setStudent(user);
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    @Override
    public Page<ReviewResponse> getAllAndSearch(Long courseId, ReviewFilterRequest filter, Pageable pageable) {
        return reviewRepository.findReviewByCourseId(courseId, filter, pageable)
                .map(reviewMapper::toReviewResponse);
    }

    @Override
    public ReviewResponse updateReview(Long id, UpdateReviewRequest request) {
        Review review = getById(id);
        reviewValidator.validateOwnerAndAdminRole(review,
                "Bạn không có quyền cập nhật bài đánh giá này");
        reviewMapper.updateReviewFromDto(request, review);

        return reviewMapper.toReviewResponse(
                reviewRepository.save(review)
        );
    }

    @Override
    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id, ERRORCODE.REVIEW_NOTFOUND));
    }

    @Override
    public void deleteById(Long id) {
        Review review = getById(id);
        reviewValidator.validateOwnerAndAdminRole(review,
                "Bạn không có quyền xoá bài đánh giá này");
        reviewRepository.delete(review);
    }
}
