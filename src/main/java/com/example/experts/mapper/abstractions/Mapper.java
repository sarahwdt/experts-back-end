package com.example.experts.mapper.abstractions;


import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.entity.abstraction.BaseEntity;

/**
 * Интерфейс для использования маппера
 * @param <E> сущность
 * @param <D>дто
 */
public interface Mapper<E extends BaseEntity, D extends BaseDTO> {
    E toEntity(D dto);

    D toDto(E entity);
}