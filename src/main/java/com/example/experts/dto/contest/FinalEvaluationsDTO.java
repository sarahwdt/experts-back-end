package com.example.experts.dto.contest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FinalEvaluationsDTO {
    private ProjectDTO project;
    private float evaluation;
}
