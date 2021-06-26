package com.example.experts.mapper.contest;

import com.example.experts.dto.contest.ContestDTO;
import com.example.experts.dto.contest.FinalEvaluationsDTO;
import com.example.experts.entity.contest.Contest;
import com.example.experts.entity.contest.Indicator;
import com.example.experts.entity.contest.IndicatorEvaluation;
import com.example.experts.mapper.abstractions.AbstractMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ContestMapper extends AbstractMapper<Contest, ContestDTO> {
    private final ProjectMapper projectMapper;
    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Contest.class, ContestDTO.class)
                .addMappings(m -> m.skip(ContestDTO::setFinalEvaluations)).setPostConverter(toDtoConverter());
    }

    /**
     * Установка поля с финальными оценками проектов
     * @param source конкурс
     * @param destination Конкурс дто
     */
    @Override
    protected void mapSpecificFields(Contest source, ContestDTO destination) {
        if (source.getProjectEvaluationList() == null || source.getIndicatorEvaluationList() == null
                || source.getProjectEvaluationList().stream()
                .anyMatch(projectEvaluation -> projectEvaluation.getEvaluation() == null)
                || source.getIndicatorEvaluationList().stream().anyMatch(indicatorEvaluation ->
                indicatorEvaluation.getEvaluation() == null)) destination.setFinalEvaluations(null);
        else {
            List<FinalEvaluationsDTO> finalEvaluations = new ArrayList<>();
            source.getProjectEvaluationList().forEach(projectEvaluation -> {
                FinalEvaluationsDTO finalEvaluation = finalEvaluations.stream().filter(finalEvaluationsDTO ->
                        finalEvaluationsDTO.getProject().getId().equals(projectEvaluation.getProject().getId()))
                        .findFirst().orElse(null);
                if (finalEvaluation == null)
                    finalEvaluations.add(new FinalEvaluationsDTO(projectMapper.toDto(projectEvaluation.getProject()),
                            projectEvaluation.getEvaluation() * getIndicatorEvaluation(
                                    source.getIndicatorEvaluationList(), projectEvaluation.getIndicator())));
                else
                    finalEvaluation.setEvaluation(finalEvaluation.getEvaluation() + projectEvaluation.getEvaluation()
                            * getIndicatorEvaluation(source.getIndicatorEvaluationList(),
                            projectEvaluation.getIndicator()));
            });
            destination.setFinalEvaluations(finalEvaluations);
        }
    }


    private Float getIndicatorEvaluation(List<IndicatorEvaluation> indicatorEvaluationList, Indicator indicator) {
        return indicatorEvaluationList.stream().filter(indicatorEvaluation ->
                Objects.equals(indicatorEvaluation.getIndicator().getId(), indicator.getId()))
                .map(IndicatorEvaluation::getEvaluation)
                .findFirst().orElseThrow(NullPointerException::new);
    }
}
