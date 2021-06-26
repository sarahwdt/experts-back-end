package com.example.experts.controller.contest;

import com.example.experts.dto.contest.IndicatorsEvaluationDTO;
import com.example.experts.entity.contest.IndicatorsEvaluation;
import com.example.experts.mapper.contest.IndicatorsEvaluationMapper;
import com.example.experts.service.AuthenticationService;
import com.example.experts.service.contest.IndicatorsEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Котроллер, обрабатывающий запросы к объектам оценок критериев
 */
@RestController
@RequestMapping("/api/indicators_evaluation")
@RequiredArgsConstructor
public class IndicatorsEvaluationController {
    private final IndicatorsEvaluationService service;
    private final AuthenticationService authenticationService;
    private final IndicatorsEvaluationMapper mapper;

    /**
     * Определяет данные предоставляемые разным группам пользователей
     * @param contest_id идентификатор оцениваемого конкурса
     * @return json с оценками
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<IndicatorsEvaluationDTO>> get(@RequestParam Long contest_id) {
        List<IndicatorsEvaluation> items;
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
     * Обновляет оценки
     * @param dtos массив оценок
     * @return json
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<List<IndicatorsEvaluationDTO>> update(@RequestBody List<IndicatorsEvaluationDTO> dtos) {
        List<IndicatorsEvaluation> entities = service.update(dtos.stream().map(mapper::toEntity)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(entities.stream().map(mapper::toDto).collect(Collectors.toList()));
    }
}
