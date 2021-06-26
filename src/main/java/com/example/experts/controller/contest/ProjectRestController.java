package com.example.experts.controller.contest;

import com.example.experts.controller.abstraction.BaseCRUDController;
import com.example.experts.dto.contest.ProjectDTO;
import com.example.experts.dto.user.RoleDTO;
import com.example.experts.entity.contest.Project;
import com.example.experts.entity.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectRestController extends BaseCRUDController<Project, ProjectDTO> {
}
