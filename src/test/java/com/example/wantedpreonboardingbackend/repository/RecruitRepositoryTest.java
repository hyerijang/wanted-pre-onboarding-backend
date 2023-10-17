package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

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

        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(company)
                .build();


        //when

        Long savedId = recruitRepository.save(recruit);
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
}