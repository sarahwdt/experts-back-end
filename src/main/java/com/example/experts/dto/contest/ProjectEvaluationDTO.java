package com.example.experts.dto.contest;

import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectEvaluationDTO extends BaseDTO {
    private IndicatorDTO indicator;
    private ProjectDTO project;
    private Float evaluation;
}
