package com.example.experts.entity.contest;

import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность оценки проекта относительно другого прокета
 */
@Entity
@Table(name = "projects_evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectsEvaluation extends BaseEntity {
    @ManyToOne(cascade = CascadeType.DETACH)
    private Contest contest;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Indicator indicator;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Project first;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Project second;

    @Column(nullable = true)
    private Float evaluation;
}
