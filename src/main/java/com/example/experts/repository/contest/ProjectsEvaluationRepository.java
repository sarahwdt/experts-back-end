package com.example.experts.repository.contest;


import com.example.experts.entity.contest.ProjectsEvaluation;
import com.example.experts.repository.abstraction.BaseCRUDRepository;

import java.util.List;

public interface ProjectsEvaluationRepository<PK extends Long> extends BaseCRUDRepository<ProjectsEvaluation> {
    List<ProjectsEvaluation> findAllByContest_Id(Long contestId);

    List<ProjectsEvaluation> findAllByContest_IdAndUser_Id(Long contest_id, Long user_id);
}
