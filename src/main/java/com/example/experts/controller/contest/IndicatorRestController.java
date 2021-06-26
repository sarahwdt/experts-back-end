package com.example.experts.controller.contest;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.contest.IndicatorDTO;
import com.example.experts.dto.contest.ProjectDTO;
import com.example.experts.entity.contest.Indicator;
import com.example.experts.entity.contest.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indicator")
@RequiredArgsConstructor
public class IndicatorRestController extends BaseCRUDController<Indicator, IndicatorDTO> {
}
