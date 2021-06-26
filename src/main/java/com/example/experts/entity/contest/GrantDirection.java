package com.example.experts.entity.contest;

import com.example.experts.entity.abstraction.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность гратового направленияя
 */
@Entity
@Table(name = "grant_directions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GrantDirection extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
