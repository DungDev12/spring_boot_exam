package com.example.srs.models.entities;

import com.example.srs.commons.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "course_id")
    private Course course;

    @NotNull
    private String title;

    @Column(length = 500)
    private String contentUrl;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Positive
    @NotNull
    private int orderIndex;

    @Column(
            name = "is_published",
            nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE"
    )
    private boolean published = false;

    @OneToMany(mappedBy = "lesson")
    private List<LessonProgress> lessonProgresses;
}
