package com.example.experts.service.contest;

import com.example.experts.entity.contest.IndicatorsEvaluation;
import com.example.experts.repository.contest.IndicatorsEvaluationRepository;
import com.example.experts.service.abstractions.BaseCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * КЛасс определяющий работу с оценками Индикаторов
 */
@Service
@RequiredArgsConstructor
public class IndicatorsEvaluationService extends BaseCRUDService<IndicatorsEvaluation> {
    private final IndicatorsEvaluationRepository<Long> indicatorsEvaluationRepository;
    private final ContestService contestService;

    /**
     * Метод получени пар оценко для конкурса и пользователя
     * @param contestId конкурс
     * @param userId пользователь
     * @return список пар и оценок
     */
    public List<IndicatorsEvaluation> getAll(long contestId, long userId) {
        return indicatorsEvaluationRepository.findAllByContest_IdAndUser_Id(contestId, userId);
    }

    /**
     * Метод получени пар оценко для конкурса
     * @param contestId конкурс
     * @return список пар и оценок
     */
    public List<IndicatorsEvaluation> getAll(long contestId) {
        return indicatorsEvaluationRepository.findAllByContest_Id(contestId);
    }

    /**
     * Метод обновления оценок
     * @param entities оценки
     * @return список обновленных оценок
     */
    public List<IndicatorsEvaluation> update(List<IndicatorsEvaluation> entities) {
        List<IndicatorsEvaluation> saved = indicatorsEvaluationRepository.saveAll(entities);
        Set<Long> contestIds = entities.stream().map(indicatorsEvaluation -> indicatorsEvaluation.getContest().getId())
                .collect(Collectors.toSet());

        contestIds.forEach(contestService::updateIndicatorEvaluation);
        return saved;
    }
}
