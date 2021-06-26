package com.example.experts.controller.user;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.user.UserDTO;
import com.example.experts.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер обрабатывающий запросы к пользователям
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController extends BaseCRUDController<User, UserDTO> {
    /**
     * Обновление данных пользователя
     * @param id идетификатор пользователя
     * @param entity данные пользователя
     * @return json с обновленными данными
     */
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO entity) {
        return baseROService.get(id)
                .map(oldEntity -> {
                    User newEntity = baseMapper.toEntity(entity);
                    newEntity.setLogin(oldEntity.getLogin());
                    newEntity.setPassword(oldEntity.getPassword());
                    return baseMapper
                            .toDto(baseCRUDService
                                    .update(newEntity));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
