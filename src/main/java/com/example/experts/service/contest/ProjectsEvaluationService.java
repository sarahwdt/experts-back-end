package com.example.experts.service.contest;

import com.example.experts.entity.contest.IndicatorsEvaluation;
import com.example.experts.entity.contest.ProjectsEvaluation;
import com.example.experts.repository.contest.ProjectsEvaluationRepository;
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
public class ProjectsEvaluationService extends BaseCRUDService<IndicatorsEvaluation> {
    private final ProjectsEvaluationRepository<Long> projectsEvaluationRepository;
    private final ContestService contestService;

    /**
     * Метод получени пар оценко для конкурса и пользователя
     *
     * @param contestId конкурс
     * @param userId    пользователь
     * @return список пар и оценок
     */
    public List<ProjectsEvaluation> getAll(long contestId, long userId) {
        return projectsEvaluationRepository.findAllByContest_IdAndUser_Id(contestId, userId);
    }

    /**
     * Метод получени пар оценко для конкурса
     *
     * @param contestId конкурс
     * @return список пар и оценок
     */

    public List<ProjectsEvaluation> getAll(long contestId) {
        return projectsEvaluationRepository.findAllByContest_Id(contestId);
    }

    /**
     * Метод обновления оценок
     *
     * @param entities оценки
     * @return список обновленных оценок
     */
    public List<ProjectsEvaluation> update(List<ProjectsEvaluation> entities) {
        List<ProjectsEvaluation> saved = projectsEvaluationRepository.saveAll(entities);
        Set<Long> contestIds = entities.stream().map(projectsEvaluation -> projectsEvaluation.getContest().getId())
                .collect(Collectors.toSet());
        contestIds.forEach(contestService::updateProjectEvaluation);
        return saved;
    }
}
