package com.example.experts.repository.contest;


import com.example.experts.entity.contest.Contest;
import com.example.experts.entity.user.User;
import com.example.experts.repository.abstraction.BaseCRUDRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestRepository extends BaseCRUDRepository<Contest> {
    Page<Contest> findAllByDeletedFalseAndUsers(User user, Pageable pageable);
}
