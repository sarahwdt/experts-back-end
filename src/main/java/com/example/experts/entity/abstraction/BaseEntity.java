package com.example.experts.entity.abstraction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Базовый класс сущности
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<Long> {
    @Column(nullable = false)
    private boolean deleted = false;

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
