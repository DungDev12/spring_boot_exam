package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import com.example.srs.enums.StatusCourses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends BaseEntity {

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Column(
            precision = 10,
            scale = 2,
            nullable = false,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0.00"
    )
    private BigDecimal price = BigDecimal.ZERO;

    private int durationHours;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 50
    )
    private StatusCourses status = StatusCourses.DRAFT;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews;
}
