package com.example.experts.entity.user.info;

import com.example.experts.entity.abstraction.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность направления научной работы
 */
@Entity
@Table(name = "scientific_directions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScientificDirection extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
