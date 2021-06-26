package com.example.experts.controller.contest;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.contest.GrantDirectionDTO;
import com.example.experts.dto.user.info.RankDTO;
import com.example.experts.entity.contest.GrantDirection;
import com.example.experts.entity.user.info.Rank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grant_direction")
@RequiredArgsConstructor
public class GrantDirectionRestController extends BaseCRUDController<GrantDirection, GrantDirectionDTO> {
}
