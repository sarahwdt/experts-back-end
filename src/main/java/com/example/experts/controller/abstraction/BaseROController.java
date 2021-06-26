package com.example.experts.controller.abstraction;


import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.mapper.abstractions.Mapper;
import com.example.experts.service.abstractions.BaseROService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Базовый контроллер обработки запросов get
 *
 * @param <Entity> используемая сущность
 * @param <DTO>    представление сущности
 */
@RestController
@RequiredArgsConstructor
public abstract class BaseROController<Entity extends BaseEntity, DTO extends BaseDTO> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseROService<Entity> baseROService;
    @Setter(onMethod_ = {@Autowired})
    protected Mapper<Entity, DTO> baseMapper;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<DTO>> get(Pageable pageable) {
        Page<Entity> page = baseROService.getAll(pageable);
        return ResponseEntity.ok(new PageImpl<>(baseROService.getAll(pageable).stream()
                .map(entity -> baseMapper
                        .toDto(entity))
                .collect(Collectors.toList()), page.getPageable(), page.getTotalElements()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DTO> get(@PathVariable Long id) {
        return baseROService.get(id).map(entity -> baseMapper
                .toDto(entity))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
