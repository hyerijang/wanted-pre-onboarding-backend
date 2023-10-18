package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class RecruitRepositoryTest {

    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager em;

    public Company saveCompany(String name) {
        Company company = new Company();
        company.setName(name);
        companyRepository.save(company); //회사 저장
        return company;
    }


    @DisplayName("공고 등록")
    @Test
    void save() {

        //given
        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(saveCompany("원티드"))
                .build();

        //when
        em.persist(recruit);

        //than
        Assertions.assertThat(recruit.getId()).isNotNull();
    }

    @DisplayName("공고 수정")
    @Test
    void edit() {
        Long id = saveRecruits().keySet().iterator().next();
        Recruit find = em.find(Recruit.class, id);

        //공고수정
        find.setSkills("HTML");//사용 기술 수정
        find.setContent("원티드랩에서 프론트엔드엔드 주니어 개발자를 채용합니다. 자격요건은..");//내용 수정
        find.setReward(77777L);//리워드 수정
        find.setPosition("프론트엔드엔드 주니어 개발자");//포지션 수정
        //when
        Recruit result = recruitRepository.findOne(id);
        //than
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getSkills()).isEqualTo("HTML");
        Assertions.assertThat(result.getReward()).isEqualTo(77777L);
        Assertions.assertThat(result.getContent()).isEqualTo("원티드랩에서 프론트엔드엔드 주니어 개발자를 채용합니다. 자격요건은..");
        Assertions.assertThat(result.getPosition()).isEqualTo("프론트엔드엔드 주니어 개발자");

    }

    private Map<Long, Recruit> saveRecruits() {
        //테스트용 샘플데이터
        Map<Long, Recruit> savedRecruitList = new HashMap<>();
        //1
        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(saveCompany("원티드"))
                .build();
        Long id = recruitRepository.save(recruit);
        savedRecruitList.put(id, recruit);
        //2
        Recruit recruit2 = Recruit.builder()
                .skills("AWS")
                .position("네트워크 엔지니어")
                .reward(9000000L)
                .content("네트워크 엔지니어 - 리눅스 마스터, 정보처리기사 우대")
                .company(saveCompany("네이버"))
                .build();
        Long id2 = recruitRepository.save(recruit2);
        savedRecruitList.put(id2, recruit2);

        return savedRecruitList;
    }

    @DisplayName("채용 공고 목록에는 삭제된 공고가 포함되어서는 안된다.")
    @Test
    void list() {
        //given
        Map<Long, Recruit> savedRecruitList = saveRecruits();
        Long id = savedRecruitList.keySet().iterator().next();
        Recruit one = recruitRepository.findOne(id);
        one.deleteRecruit(); // 공고 1개 삭제
        int offset = 0;
        int limit = 0;
        //when
        List<Recruit> result = em.createQuery("select r from Recruit r join fetch  r.company c where r.isDeleted = False ", Recruit.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        //than
        for (Recruit r : result) {
            Assertions.assertThat(r.isDeleted()).isEqualTo(Boolean.FALSE); // 삭제되지 않은 공고만 조회되어야한다.
        }
    }


    @DisplayName("Id로 단건 조회")
    @Test
    void findOne() {
        //given
        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(saveCompany("원티드"))
                .build();
        Long savedId = recruitRepository.save(recruit);

        //when
        Recruit result = em.find(Recruit.class, savedId);
        //than
        assertEquals(savedId, result.getId(), "Id가 동일해야한다");
        assertEquals("Python", result.getSkills(), "skills가 동일해야한다");
        assertEquals(1000000L, result.getReward(), "reward가 동일해야한다");
        assertEquals("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", result.getContent(), "reward가 동일해야한다");
        assertEquals("원티드", result.getCompany().getName(), "회사 정보가 동일해야한다");
    }

}