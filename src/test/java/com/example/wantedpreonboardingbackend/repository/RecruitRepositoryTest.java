package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import com.example.wantedpreonboardingbackend.dto.EditRecruitRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class RecruitRepositoryTest {

    @Autowired
    private RecruitRepository recruitRepository;


    private Company company;

    @BeforeEach
    public void init() {
        company = new Company();
        company.setId(1234567L);
        company.setName("원티드");
    }


    @DisplayName("공고 등록")
    @Test
    void save() {
        Long savedId = saveRecruits().keySet().iterator().next();
        Recruit savedRecruit = recruitRepository.findOne(savedId);

        //than
        Assertions.assertThat(savedRecruit.getId()).isNotNull();
        Assertions.assertThat(savedRecruit.getSkills()).isEqualTo("Python");
        Assertions.assertThat(savedRecruit.getReward()).isEqualTo(1000000L);
        Assertions.assertThat(savedRecruit.getContent()).isEqualTo("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        Assertions.assertThat(savedRecruit.getPosition()).isEqualTo("백엔드 주니어 개발자");
        Assertions.assertThat(savedRecruit.getCompany().getId()).isEqualTo(1234567L);
        Assertions.assertThat(savedRecruit.getCompany().getName()).isEqualTo("원티드");
    }

    @DisplayName("공고 수정")
    @Test
    void edit() {
        Long id = saveRecruits().keySet().iterator().next();

        EditRecruitRequest editRecruitRequest = new EditRecruitRequest();
        editRecruitRequest.setContent("원티드랩에서 프론트엔드엔드 주니어 개발자를 채용합니다. 자격요건은.."); //내용 수정
        editRecruitRequest.setReward(77777L); //리워드 수정
        editRecruitRequest.setPosition("프론트엔드엔드 주니어 개발자"); //포지션수정
        editRecruitRequest.setSkills("HTML");//사용기술 수정

        //when
        Recruit savedRecruit = recruitRepository.findOne(id);
        savedRecruit.editRecruit(editRecruitRequest);
        //than
        Assertions.assertThat(savedRecruit.getId()).isNotNull();
        Assertions.assertThat(savedRecruit.getSkills()).isEqualTo("HTML");
        Assertions.assertThat(savedRecruit.getReward()).isEqualTo(77777L);
        Assertions.assertThat(savedRecruit.getContent()).isEqualTo("원티드랩에서 프론트엔드엔드 주니어 개발자를 채용합니다. 자격요건은..");
        Assertions.assertThat(savedRecruit.getPosition()).isEqualTo("프론트엔드엔드 주니어 개발자");
        Assertions.assertThat(savedRecruit.getCompany().getId()).isEqualTo(1234567L); // 회사 정보는 변경되지않아야함
        Assertions.assertThat(savedRecruit.getCompany().getName()).isEqualTo("원티드");  // 회사 정보는 변경되지않아야함
    }

    private Map<Long, Recruit> saveRecruits() {
        //테스트용 샘플데이터
        Map<Long, Recruit> recruitList = new HashMap<>();
        //1
        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(company)
                .build();
        Long id = recruitRepository.save(recruit);
        recruitList.put(id, recruit);
        //2
        Recruit recruit2 = Recruit.builder()
                .skills("AWS")
                .position("네트워크 엔지니어")
                .reward(9000000L)
                .content("네트워크 엔지니어 - 리눅스 마스터, 정보처리기사 우대")
                .company(company)
                .build();
        Long id2 = recruitRepository.save(recruit2);
        recruitList.put(id2, recruit2);

        return recruitList;
    }
}