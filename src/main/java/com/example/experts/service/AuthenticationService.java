package com.example.experts.service;

import com.example.experts.entity.user.User;
import com.example.experts.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Класс расширяющий работу с объектом аутентификации пользователя
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;

    /**
     * Проверить наличие роли
     * @param roleName имя
     * @return наличие отсутствие
     */
    public boolean hasRole(String roleName) {
        return roleRepository.findByNameAndDeletedFalse(roleName).map(role -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal instanceof User && ((User) principal).getAuthorities().contains(role);
        }).orElse(false);
    }

    /**
     * Получение айди текущего пользователя
     */
    public Long getCurrentId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof User? ((User) principal).getId(): null;
    }

    /**
     * Получение данных текущего пользователя
     * @return Данные
     */
    public User getCurrent(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof User? ((User) principal): null;
    }
}
