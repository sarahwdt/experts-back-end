package com.example.experts.dto.user;


import com.example.experts.dto.user.info.CredentialDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends CredentialDTO {
    private String login;
    private List<RoleDTO> roles;
}
