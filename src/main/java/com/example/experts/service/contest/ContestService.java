package com.example.experts.service.contest;

import com.example.experts.entity.contest.*;
import com.example.experts.entity.user.Role;
import com.example.experts.entity.user.User;
import com.example.experts.repository.contest.*;
import com.example.experts.repository.user.RoleRepository;
import com.example.experts.service.IterativeService;
import com.example.experts.service.abstractions.BaseCRUDService;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс описывающий работу с конкурсами
 */
@Service
@RequiredArgsConstructor
public class ContestService extends BaseCRUDService<Contest> {
    private final ProjectsEvaluationRepository<Long> projectsEvaluationRepository;
    private final IndicatorsEvaluationRepository<Long> indicatorsEvaluationRepository;
    private final IndicatorEvaluationRepository indicatorEvaluationRepository;
    private final ProjectEvaluationRepository projectEvaluationRepository;
    private final ContestRepository contestRepository;
    private final RoleRepository roleRepository;
    private final IterativeService iterativeService;

    /**
     * Создание конкурса
     * @param entity сущность конкурса
     * @return сохраненная сущность
     */
    @Override
    public Contest create(Contest entity) {
        Contest contest = super.create(entity);
        for (User user : entity.getUsers()) {
            Queue<Indicator> indicators = new ArrayDeque<>(entity.getIndicators());
            for (Indicator iter = indicators.poll(); iter != null; iter = indicators.poll()) {
                Queue<Indicator> availablePairs = new ArrayDeque<>(indicators);
                for (Indicator pair = availablePairs.poll(); pair != null; pair = availablePairs.poll())
                    indicatorsEvaluationRepository.save(new IndicatorsEvaluation(contest, user, iter, pair,
                            null));
            }
        }

        for (User user : entity.getUsers()) {
            for (Indicator indicator : entity.getIndicators()) {
                Queue<Project> projects = new ArrayDeque<>(entity.getProjects());
                for (Project iter = projects.poll(); iter != null; iter = projects.poll()) {
                    Queue<Project> availablePairs = new ArrayDeque<>(projects);
                    for (Project pair = availablePairs.poll(); pair != null; pair = availablePairs.poll())
                        projectsEvaluationRepository.save(new ProjectsEvaluation(contest, user, indicator, iter, pair,
                                null));
                }
            }
        }
        return contestRepository.save(entity);
    }

    /**
     * Получение списка сущностей
     * @param pageable конфигурация страницы
     * @return постраничный список
     */
    @Override
    public Page<Contest> getAll(Pageable pageable) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = principal instanceof User ? (User) principal : null;
        Role root = roleRepository.findByNameAndDeletedFalse("ROLE_ROOT").orElse(null);
        Role expert = roleRepository.findByNameAndDeletedFalse("ROLE_EXPERT").orElse(null);
        if (currentUser == null) return Page.empty();
        if (root != null && currentUser.getAuthorities().contains(root))
            return super.getAll(pageable);
        else if (expert != null && currentUser.getAuthorities().contains(expert))
            return contestRepository.findAllByDeletedFalseAndUsers(currentUser, pageable);
        else
            return Page.empty();
    }

    /**
     * Построение матрцы для итерационного процесса
     * @param indicatorsSummary список с суммарныси оценками по каждому критерию
     * @param indicators список критериев
     * @param <Label> класс
     * @return матрица оценок
     */
    private <Label> List<List<Float>> buildEvaluationMatrix(Map<Pair<Label, Label>, Float> indicatorsSummary,
                                                            List<Label> indicators) {
        List<List<Float>> result = new LinkedList<>();
        for (Label row : indicators) {
            List<Float> items = new LinkedList<>();
            for (Label column : indicators) {
                if (row == column)
                    items.add(1f);
                else {
                    indicatorsSummary.forEach((pair, eval) -> {
                        if (pair.getFirst() == row && pair.getSecond() == column)
                            items.add(eval);
                        else if (pair.getFirst() == column && pair.getSecond() == row)
                            items.add(2 - eval);
                    });
                }
            }
            result.add(items);
        }
        return result;
    }

    /**
     * Метод подсчитывающий суммарную оценку
     * @param y количество экспертов
     * @param x сумма оценки
     * @return суммарная оценка
     */
    private Float summaryEvaluation(int y, float x) {
        float fy = (float) y;
        if (fy < x - (float) 0.5)
            return (float) 0.5;
        else if (fy > x + (float) 0.5)
            return (float) 1.5;
        else
            return (float) 1;
    }

    /**
     * Мето округления
     * @param target округляемая величина
     * @return округленная величина
     */
    private Float round(float target){
        return Math.round(target * 1000.0f)/1000.0f;
    }

    /**
     * Метод посчета относительных весов критериев
     * @param id идентификатор конкурса
     */
    public void updateIndicatorEvaluation(Long id) {
        List<IndicatorsEvaluation> evaluations = indicatorsEvaluationRepository.findAllByContest_Id(id);
        if (evaluations.stream().anyMatch(evaluation -> evaluation.getEvaluation() == null)) return;

        Set<Indicator> indicators = evaluations.stream().map(IndicatorsEvaluation::getFirst).collect(Collectors.toSet());
        indicators.addAll(evaluations.stream().map(IndicatorsEvaluation::getSecond).collect(Collectors.toSet()));
        Set<User> experts = evaluations.stream().map(IndicatorsEvaluation::getUser).collect(Collectors.toSet());

        List<Indicator> indicatorsList = List.copyOf(indicators);

        List<Float> finalEvaluations = iterativeService.iterationProcess(buildEvaluationMatrix(
                indicatorsSummary(evaluations, experts.size()), indicatorsList));

        contestRepository.findByIdAndDeletedFalse(id).ifPresent(contest -> {
            List<IndicatorEvaluation> finalIndicatorEvaluationList = new ArrayList<>();

            for (int i = 0; i < indicatorsList.size(); i++) {
                finalIndicatorEvaluationList.add(indicatorEvaluationRepository.save(
                        new IndicatorEvaluation(indicatorsList.get(i), round(finalEvaluations.get(i)))));
            }
            contest.setIndicatorEvaluationList(finalIndicatorEvaluationList);
            contestRepository.save(contest);
        });
    }

    /**
     * Созадние списка суммарных оценок для кажой пары критериев
     * @param evaluations Список оценок
     * @param expertsNumber количество экспертов
     * @return спиок пар и оценок
     */
    private Map<Pair<Indicator, Indicator>, Float> indicatorsSummary(List<IndicatorsEvaluation> evaluations,
                                                                     int expertsNumber) {
        Map<Pair<Indicator, Indicator>, Float> result = new HashMap<>();
        indicatorsPairEvaluations(evaluations).forEach((indicatorIndicatorPair, floats) ->
                result.put(indicatorIndicatorPair,
                        summaryEvaluation(expertsNumber, floats.stream().reduce((float) 0, Float::sum))));
        return result;
    }

    /**
     * Список пар критериев и оценок по ним
     * @param evaluations список оценок
     * @return список пар критериев и оценок по нма
     */
    private Map<Pair<Indicator, Indicator>, List<Float>> indicatorsPairEvaluations(List<IndicatorsEvaluation> evaluations) {
        Map<Pair<Indicator, Indicator>, List<Float>> result = new HashMap<>();
        evaluations.forEach(evaluation -> {
            Pair<Indicator, Indicator> pair = new Pair<>(evaluation.getFirst(), evaluation.getSecond());
            Pair<Indicator, Indicator> similarPair = new Pair<>(evaluation.getSecond(), evaluation.getFirst());
            if (result.get(pair) != null)
                result.get(pair).add(evaluation.getEvaluation());
            else if (result.get(similarPair) != null)
                result.get(similarPair).add(2 - evaluation.getEvaluation());
            else
                result.put(pair, new LinkedList<>(Collections.singleton(evaluation.getEvaluation())));
        });
        return result;
    }


    /**
     * Метод посчета относительных весов проектов
     * @param id идентификатор конкурса
     */
    public void updateProjectEvaluation(Long id) {
        List<ProjectsEvaluation> evaluations = projectsEvaluationRepository.findAllByContest_Id(id);
        if (evaluations.stream().anyMatch(evaluation -> evaluation.getEvaluation() == null)) return;

        Set<Project> projects = evaluations.stream().map(ProjectsEvaluation::getFirst).collect(Collectors.toSet());
        projects.addAll(evaluations.stream().map(ProjectsEvaluation::getSecond).collect(Collectors.toSet()));
        Set<User> experts = evaluations.stream().map(ProjectsEvaluation::getUser).collect(Collectors.toSet());

        Map<Indicator, List<Float>> finalEvaluation = new HashMap<>();
        List<Project> projectsList = List.copyOf(projects);

        indicatorProjectsPairSummaryGroup(evaluations, experts.size()).forEach((indicator, pairFloatMap) ->
                finalEvaluation.put(indicator, iterativeService.iterationProcess(buildEvaluationMatrix(pairFloatMap,
                        projectsList)))
        );

        contestRepository.findById(id).ifPresent(contest -> {
            List<ProjectEvaluation> finalProjectEvaluationList = new ArrayList<>();
            finalEvaluation.forEach((indicator, evaluationsList) -> {
                for (int i = 0; i < projectsList.size(); i++) {
                    finalProjectEvaluationList.add(projectEvaluationRepository.save(
                            new ProjectEvaluation(indicator, projectsList.get(i), round(evaluationsList.get(i)))));
                }
            });
            contest.setProjectEvaluationList(finalProjectEvaluationList);
            contestRepository.save(contest);
        });
    }

    /**
     * Созадние списка суммарных оценок для кажой пары проектов по каждому критерию
     * @param evaluations Список оценок
     * @return спиок пар и оценок
     */
    private Map<Indicator, Map<Pair<Project, Project>, List<Float>>> indicatorProjectsPairEvaluationsGroup(
            List<ProjectsEvaluation> evaluations) {
        Map<Indicator, Map<Pair<Project, Project>, List<Float>>> result = new HashMap<>();
        evaluations.forEach(projectsEvaluation -> {

            Pair<Project, Project> pair = new Pair<>(projectsEvaluation.getFirst(), projectsEvaluation.getSecond());
            Pair<Project, Project> similarPair = new Pair<>(projectsEvaluation.getSecond(), projectsEvaluation.getFirst());

            Map<Pair<Project, Project>, List<Float>> projectsPairEvaluation
                    = result.computeIfAbsent(projectsEvaluation.getIndicator(), k -> new HashMap<>());
            if (projectsPairEvaluation.get(pair) != null)
                projectsPairEvaluation.get(pair).add(projectsEvaluation.getEvaluation());
            else if (projectsPairEvaluation.get(similarPair) != null)
                projectsPairEvaluation.get(similarPair).add(2 - projectsEvaluation.getEvaluation());
            else
                projectsPairEvaluation.put(pair, new ArrayList<>(Collections.singleton(projectsEvaluation.getEvaluation())));

        });
        return result;
    }

    /**
     * Список суммарных оценок для каждой пары проектов по индексам
     * @param evaluations список оценок
     * @param expertsNumber количество экспертов
     * @return список оценок проектов
     */
    private Map<Indicator, Map<Pair<Project, Project>, Float>> indicatorProjectsPairSummaryGroup(
            List<ProjectsEvaluation> evaluations, int expertsNumber) {
        Map<Indicator, Map<Pair<Project, Project>, Float>> result = new HashMap<>();
        indicatorProjectsPairEvaluationsGroup(evaluations).forEach((indicator, pairListMap) ->
                pairListMap.forEach((projectProjectPair, floats) -> {
                    Map<Pair<Project, Project>, Float> indicatorMap = result.computeIfAbsent(indicator,
                            k -> new HashMap<>());

                    indicatorMap.put(projectProjectPair, summaryEvaluation(expertsNumber,
                            floats.stream().reduce(0f, Float::sum)));
                }));
        return result;
    }

}