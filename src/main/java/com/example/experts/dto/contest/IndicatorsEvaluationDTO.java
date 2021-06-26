package com.example.experts.dto.contest;

import com.example.experts.dto.abstraction.BaseDTO;
import com.example.experts.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IndicatorsEvaluationDTO extends BaseDTO {
    private ContestDTO contest;
    private UserDTO user;
    private IndicatorDTO first;
    private IndicatorDTO second;
    private Float evaluation;
}
