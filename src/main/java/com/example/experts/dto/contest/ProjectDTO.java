package com.example.experts.dto.contest;

import com.example.experts.dto.abstraction.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectDTO extends BaseDTO {
    private String name;
    private String description;
    private byte[] file;
}
