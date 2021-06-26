package com.example.experts.repository.user;


import com.example.experts.entity.user.Role;
import com.example.experts.repository.abstraction.BaseCRUDRepository;

import java.util.Optional;

public interface RoleRepository extends BaseCRUDRepository<Role> {
    Optional<Role> findByNameAndDeletedFalse(String name);
}
