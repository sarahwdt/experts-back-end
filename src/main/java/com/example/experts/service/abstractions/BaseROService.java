package com.example.experts.service.abstractions;

import com.example.experts.entity.abstraction.BaseEntity;
import com.example.experts.repository.abstraction.BaseCRUDRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * БАзовый класс определяющий работу с неизменяемой сущностбю
 * @param <Entity> класс сущности
 */
@Service
@RequiredArgsConstructor
public abstract class BaseROService<Entity extends BaseEntity> {

    @Setter(onMethod_ = {@Autowired})
    protected BaseCRUDRepository<Entity> baseRepository;

    public Optional<Entity> get(Long id) {
        return baseRepository.findByIdAndDeletedFalse(id);
    }

    public Page<Entity> getAll(Pageable pageable) {
        return baseRepository.findByDeletedFalse(pageable);
    }

    public Page<Entity> getAll() {
        return getAll(PageRequest.of(0, (int) baseRepository.count()));
    }
}
