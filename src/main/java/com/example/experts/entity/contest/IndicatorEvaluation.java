package com.example.experts.entity.contest;

import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность относительной оценки критерия
 */
@Entity
@Table(name = "indicator_evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndicatorEvaluation extends BaseEntity {

    @ManyToOne(cascade = CascadeType.DETACH)
    private Indicator indicator;

    @Column(nullable = true)
    private Float evaluation;
}
