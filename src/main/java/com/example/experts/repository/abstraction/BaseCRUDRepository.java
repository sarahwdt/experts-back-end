package com.example.experts.repository.abstraction;

import com.example.experts.entity.abstraction.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Базовый репозиторий для круд-объектов
 * @param <Entity>
 */
@NoRepositoryBean
public interface BaseCRUDRepository<Entity extends BaseEntity>
        extends JpaRepository<Entity, Long> {
    Optional<Entity> findByIdAndDeletedFalse(Long id);

    Iterable<Entity> findByDeletedFalse(Sort var1);

    Page<Entity> findByDeletedFalse(Pageable var1);

    boolean existsByIdAndDeletedFalse(Long id);

}
