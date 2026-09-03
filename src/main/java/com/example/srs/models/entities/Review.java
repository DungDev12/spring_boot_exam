package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews",uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"course_id","student_id"}
        )
})
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "student_id")
    private User student;

    @Positive
    @Column(nullable = false, check = @CheckConstraint(constraint = "rating >= 1 AND rating <=5"))
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

}
