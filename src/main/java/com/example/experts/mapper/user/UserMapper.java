package com.example.experts.mapper.user;

import com.example.experts.dto.user.UserDTO;
import com.example.experts.entity.user.User;
import com.example.experts.mapper.abstractions.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {
}
