package com.example.experts.controller.user;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.user.RoleDTO;
import com.example.experts.entity.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleRestController extends BaseCRUDController<Role, RoleDTO> {
}
