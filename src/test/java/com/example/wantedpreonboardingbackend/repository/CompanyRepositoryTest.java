package com.example.wantedpreonboardingbackend.repository;

import com.example.wantedpreonboardingbackend.domain.Company;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @DisplayName("회사 등록")
    @Test
    void save() {
        //given
        Company company = new Company();
        company.setName("(주)원티드");
        //when
        Long savedId = companyRepository.save(company);
        Company savedCompany = companyRepository.findOne(savedId);
        //than
        Assertions.assertThat(savedCompany.getId()).isNotNull();
        Assertions.assertThat(savedCompany.getName()).isEqualTo("(주)원티드");

    }
}