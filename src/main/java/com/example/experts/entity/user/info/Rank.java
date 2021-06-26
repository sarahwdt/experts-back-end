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
 * Сущность научного звания
 */
@Entity
@Table(name = "ranks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Rank extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
