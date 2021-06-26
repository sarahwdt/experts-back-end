package com.example.experts.controller.abstraction;

import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.service.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Базовый контроллер для обработки запросов post, put, delete
 *
 * @param <Entity> используемая сущность
 * @param <DTO>    представление сущности
 */
@RestController
@RequiredArgsConstructor
public abstract class BaseCRUDController<Entity extends BaseEntity, DTO extends BaseDTO>
        extends BaseROController<Entity, DTO> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseCRUDService<Entity> baseCRUDService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<DTO> create(@RequestBody DTO entity) {
        return ResponseEntity
                .ok(baseMapper
                        .toDto(baseCRUDService
                                .create(baseMapper
                                        .toEntity(entity))));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO entity) {
        return baseROService.get(id)
                .map(oldEntity -> baseMapper
                        .toDto(baseCRUDService
                                .update(baseMapper
                                        .toEntity(entity))))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return baseROService.get(id)
                .map(entity -> baseCRUDService
                        .delete(entity))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}