package com.example.experts.repository.user;


import com.example.experts.entity.user.User;
import com.example.experts.repository.abstraction.BaseCRUDRepository;

import java.util.Optional;

public interface UserRepository extends BaseCRUDRepository<User> {
    Optional<User> findByLoginAndDeletedFalse(String login);
}
