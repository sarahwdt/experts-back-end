package com.example.experts.dto.user;

import com.example.experts.dto.abstraction.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends BaseDTO {
    private String name;
}
