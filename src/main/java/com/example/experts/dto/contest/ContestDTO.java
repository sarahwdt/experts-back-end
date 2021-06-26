package com.example.experts.dto.contest;

import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContestDTO extends BaseDTO {
    private String name;
    private GrantDirectionDTO direction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<IndicatorDTO> indicators;
    private List<UserDTO> users;
    private List<ProjectDTO> projects;
    private List<IndicatorEvaluationDTO> indicatorEvaluationList;
    private List<ProjectEvaluationDTO> projectEvaluationList;
    private List<FinalEvaluationsDTO> finalEvaluations;
}
