package com.example.srs.models.mapper;

import com.example.srs.models.entities.Review;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.review.CreateReviewRequest;
import com.example.srs.models.entities.dto.request.review.UpdateReviewRequest;
import com.example.srs.models.entities.dto.response.review.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    Review toReview(CreateReviewRequest request);
    default String map(Role role) {
        return role != null ? role.getName() : null;
    }

    @Mapping(
            source = "student.person.fullName",
            target = "studentName"
    )
    ReviewResponse toReviewResponse(Review review);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "student", ignore = true)
    void updateReviewFromDto(
            UpdateReviewRequest request,
            @MappingTarget Review review
    );
}
