package com.example.experts.controller.user.info;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.user.RoleDTO;
import com.example.experts.dto.user.info.PositionDTO;
import com.example.experts.entity.user.Role;
import com.example.experts.entity.user.info.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
public class PositionRestController extends BaseCRUDController<Position, PositionDTO> {
}
