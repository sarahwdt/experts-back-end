package com.example.experts.service.abstractions;

import com.example.experts.entity.abstraction.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Базовый класс определяющий работу с изменяемой сущность
 * @param <Entity> калсс сущности
 */
@Service
@RequiredArgsConstructor
public abstract class BaseCRUDService<Entity extends BaseEntity> extends BaseROService<Entity> {
    public Entity create(Entity entity) {
        return baseRepository.save(entity);
    }

    public Entity update(Entity entity) {
        return baseRepository.save(entity);
    }

    public boolean delete(Entity entity) {
        entity.setDeleted(true);
        baseRepository.save(entity);
        return baseRepository.existsByIdAndDeletedFalse(Objects.requireNonNull(entity.getId()));
    }

}
