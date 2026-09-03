package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import com.example.srs.enums.StatusEnrollments;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enrollments",
uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"student_id","course_id"}
        )
})
public class Enrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 50
    )
    private StatusEnrollments status = StatusEnrollments.ENROLLED;

    private LocalDateTime completionDate;

    @Column(
            precision = 5,
            scale = 2,
            nullable = false,
            columnDefinition = "DECIMAL(5,2) DEFAULT 0.00 CHECK (progress_percentage >= 0)"
    )
    private BigDecimal progressPercentage = BigDecimal.ZERO;

    @OneToMany(mappedBy = "enrollment")
    private List<LessonProgress> lessonProgresses;
}
