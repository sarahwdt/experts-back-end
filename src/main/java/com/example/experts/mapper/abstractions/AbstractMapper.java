package com.example.experts.mapper.abstractions;


import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.entity.abstraction.BaseEntity;
import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Класс маппер из одного класса в другой
 * @param <Entity> первый класс (Сущность)
 * @param <DTO> второй класс(дто)
 */
public abstract class AbstractMapper<Entity extends BaseEntity, DTO extends BaseDTO> implements Mapper<Entity, DTO> {

    @Setter(onMethod_ = {@Autowired})
    ModelMapper mapper;

    @SuppressWarnings("unchecked")
    protected final Class<Entity> entityClass = ((Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0]);
    @SuppressWarnings("unchecked")
    protected final Class<DTO> dtoClass = ((Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[1]);

    @Override
    public Entity toEntity(DTO dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, entityClass);
    }

    @Override
    public DTO toDto(Entity entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, dtoClass);
    }

    public Converter<Entity, DTO> toDtoConverter() {
        return context -> {
            Entity source = context.getSource();
            DTO destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public Converter<DTO, Entity> toEntityConverter() {
        return context -> {
            DTO source = context.getSource();
            Entity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    protected void mapSpecificFields(Entity source, DTO destination) {
    }

    protected void mapSpecificFields(DTO source, Entity destination) {
    }
}
