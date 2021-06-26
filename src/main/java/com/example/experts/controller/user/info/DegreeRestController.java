package com.example.experts.controller.user.info;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.user.RoleDTO;
import com.example.experts.dto.user.info.DegreeDTO;
import com.example.experts.entity.user.Role;
import com.example.experts.entity.user.info.Degree;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/degree")
@RequiredArgsConstructor
public class DegreeRestController extends BaseCRUDController<Degree, DegreeDTO> {
}
