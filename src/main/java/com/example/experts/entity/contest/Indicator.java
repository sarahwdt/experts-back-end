package com.example.experts.entity.contest;

import com.example.experts.entity.abstraction.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность критерия оценки
 */
@Entity
@Table(name = "indicators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Indicator extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
