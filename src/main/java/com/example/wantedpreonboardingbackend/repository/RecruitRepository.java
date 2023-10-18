package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecruitRepository {

    private final EntityManager em;

    public Long save(Recruit recruit) {
        em.persist(recruit);
        return recruit.getId();
    }

    public Recruit findOne(Long id) {
        //soft delete 된 객체도 함께 조회한다.
        return em.find(Recruit.class, id);
    }
}
