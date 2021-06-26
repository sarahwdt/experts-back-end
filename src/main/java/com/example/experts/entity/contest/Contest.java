package com.example.experts.entity.contest;

import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность конкурса
 */
@Entity
@Table(name = "contests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Contest extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    private GrantDirection direction;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Indicator> indicators;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Project> projects;

    /**
     * Относительные оценки критериев
     */
    @OneToMany
    private List<IndicatorEvaluation> indicatorEvaluationList;

    /**
     * Относительные оценки проектов
     */
    @OneToMany
    private List<ProjectEvaluation> projectEvaluationList;

}
