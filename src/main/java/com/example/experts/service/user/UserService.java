package com.example.experts.service.user;

import com.example.experts.entity.user.User;
import com.example.experts.repository.user.RoleRepository;
import com.example.experts.repository.user.UserRepository;
import com.example.experts.service.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

/**
 * Реализация интерфейса для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService extends BaseCRUDService<User> implements UserDetailsService {
    protected final UserRepository userRepository;
    protected final RoleRepository roleRepository;
    protected final RoleService roleService;
    @Setter(onMethod_ = {@Autowired, @Lazy})
    protected PasswordEncoder encoder;

    /**
     * Загрузка пользователя из базы
     * @param login логин
     * @return загруженный пользоваатель
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLoginAndDeletedFalse(login).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с логином " + login + " не найден"));
    }

    /**
     * Создать пользователя
     * @param user данные пользоватея
     * @return созданный пользователя
     */
    @Override
    public User create(@NotNull User user) {
        if (userRepository.findByLoginAndDeletedFalse(user.getLogin()).isPresent())
            return null;
        else {
            user.setRoles(Collections.singletonList(roleRepository.findByNameAndDeletedFalse("ROLE_CLIENT").orElse(null)));
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    /**
     * Найти пользователя по логину
     * @param login логин
     * @return найденый пользователь
     */
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLoginAndDeletedFalse(login);
    }

}
