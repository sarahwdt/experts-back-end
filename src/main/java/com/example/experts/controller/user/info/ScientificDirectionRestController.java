package com.example.experts.controller.user.info;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.user.info.ScientificDirectionDTO;
import com.example.experts.entity.user.info.ScientificDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scientific_direction")
@RequiredArgsConstructor
public class ScientificDirectionRestController extends BaseCRUDController<ScientificDirection,
        ScientificDirectionDTO> {
}
