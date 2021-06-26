package com.example.experts.repository.contest;


import com.example.experts.entity.contest.IndicatorsEvaluation;
import com.example.experts.repository.abstraction.BaseCRUDRepository;

import java.util.List;

public interface IndicatorsEvaluationRepository<PK extends Long> extends BaseCRUDRepository<IndicatorsEvaluation> {
    List<IndicatorsEvaluation> findAllByContest_Id(Long contestId);
    List<IndicatorsEvaluation> findAllByContest_IdAndUser_Id(Long contest_id, Long user_id);
}
