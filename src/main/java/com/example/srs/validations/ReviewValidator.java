package com.example.srs.validations;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.exceptions.AlreadyExistsException;
import com.example.srs.exceptions.BadRequestException;
import com.example.srs.models.entities.Course;
import com.example.srs.models.entities.Review;
import com.example.srs.models.entities.User;
import com.example.srs.repositories.EnrollmentRepository;
import com.example.srs.repositories.ReviewRepository;
import com.example.srs.securities.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CurrentUserService currentUserService;

    public void validateForCreate(Course course, User user){
        validateEnrolled(course.getId(),user.getId());
        validateDuplicationReview(course.getId(), user.getId());
    }

    public void validateEnrolled(Long courseId, Long studentId){
        if(!enrollmentRepository.existsByStudent_IdAndCourse_Id(studentId,courseId)){
            throw new BadRequestException("Bạn chưa đăng ký khoá học này", ERRORCODE.UNAUTHORIZED);
        }
    }

    public void validateDuplicationReview(Long courseId, Long studentId){
        if(reviewRepository.existsByStudent_IdAndCourse_Id(studentId,courseId)){
            throw new AlreadyExistsException("Bạn đã đánh giá khoá học trước đó rồi không thể đánh giá lại", ERRORCODE.REVIEW_DUPLICATION);
        }
    }

    public void validateOwnerAndAdminRole(Review review, String error){
        boolean isAdmin = currentUserService.isAdmin();
        boolean isOwner = currentUserService.isOwner(review.getStudent().getId());
        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException(
                    error,
                    ERRORCODE.FORBIDDEN
            );
        }
    }
}
