package com.example.experts.controller.contest;

import com.example.experts.dto.contest.ProjectsEvaluationDTO;
import com.example.experts.entity.contest.ProjectsEvaluation;
import com.example.experts.mapper.contest.ProjectsEvaluationMapper;
import com.example.experts.service.AuthenticationService;
import com.example.experts.service.contest.ProjectsEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер обрабатывающий запросы к оценкам проектов
 */
@RestController
@RequestMapping("/api/projects_evaluation")
@RequiredArgsConstructor
public class ProjectsEvaluationController {
    private final ProjectsEvaluationService service;
    private final AuthenticationService authenticationService;
    private final ProjectsEvaluationMapper mapper;

    /**
     * Определяет данные, предоставляемые разрым группам пользователей
     * @param contest_id оцениваемый конкурс
     * @return json с оценками
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectsEvaluationDTO>> get(@RequestParam Long contest_id) {
        List<ProjectsEvaluation> items;
        if (authenticationService.hasRole("ROLE_EXPERT"))
            items = service.getAll(contest_id, authenticationService.getCurrentId());
        else if (authenticationService.hasRole("ROLE_ROOT"))
            items = service.getAll(contest_id);
        else
            items = new ArrayList<>();
        return ResponseEntity.ok(items.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()));
    }

    /**
     * Обновление оценок
     * @param dtos список оценок
     * @return js
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<List<ProjectsEvaluationDTO>> update(@RequestBody List<ProjectsEvaluationDTO> dtos) {
        List<ProjectsEvaluation> entities = service.update(dtos.stream().map(mapper::toEntity)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(entities.stream().map(mapper::toDto).collect(Collectors.toList()));
    }
}
