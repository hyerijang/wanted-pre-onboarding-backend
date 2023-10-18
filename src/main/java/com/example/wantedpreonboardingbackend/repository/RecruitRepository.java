package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 삭제된 공고를 제외하고 전부 조회, fetch join
     * @return
     */
    public List<Recruit> findAll(int offset, int limit) {
        return em.createQuery("select r from Recruit r join fetch  r.company c where r.isDeleted = False ", Recruit.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
