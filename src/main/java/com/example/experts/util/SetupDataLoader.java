package com.example.experts.util;


import com.example.experts.entity.user.Role;
import com.example.experts.entity.user.User;
import com.example.experts.repository.user.RoleRepository;
import com.example.experts.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Создание данных при запуске
 */
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound("ROLE_ROOT");

        Role rootRole = roleRepository.findByNameAndDeletedFalse("ROLE_ROOT").orElseThrow();
        userRepository.findByLoginAndDeletedFalse("ROOT_USER").ifPresentOrElse(
                user -> user.setRoles(Collections.singletonList(rootRole)),
                () -> {
                    User user = new User();
                    user.setLogin("ROOT_USER");
                    user.setPassword(passwordEncoder.encode("slaves"));
                    user.setRoles(Collections.singletonList(rootRole));
                    userRepository.save(user);
                });
        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(String name) {
        return roleRepository.findByNameAndDeletedFalse(name)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(name);
                    return roleRepository.save(newRole);
                });
    }
}