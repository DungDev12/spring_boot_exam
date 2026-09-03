package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "lesson_progresses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames  = {"enrollment_id","lesson_id"})
        })
public class LessonProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(
            name = "is_completed",
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE"
    )
    private boolean completed = false;

    private LocalDateTime completedAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime lastAccessedAt;
}
