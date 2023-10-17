package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.dto.AddRecruitRequest;
import com.example.wantedpreonboardingbackend.dto.AddRecruitResponse;
import com.example.wantedpreonboardingbackend.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.repository.RecruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class RecruitServiceTest {

    @InjectMocks
    RecruitService recruitService;

    @Mock
    RecruitRepository recruitRepository;

    @Mock
    CompanyRepository companyRepository;

    public Company generateCompany(String name) {
        Company company = new Company();
        company.setId((long) Math.random());
        company.setName(name);
        return company;
    }

    @Test
    @DisplayName("공고 등록")
    void post() {
        //given
        AddRecruitRequest addRecruitRequest = new AddRecruitRequest();
        addRecruitRequest.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        addRecruitRequest.setReward(1000000L);
        addRecruitRequest.setPosition("백엔드 주니어 개발자");
        addRecruitRequest.setSkills("Python");
        Company company = generateCompany("원티드");
        addRecruitRequest.setCompanyId(company.getId());
        when(companyRepository.findOne(company.getId())).thenReturn(company);
        //when
        AddRecruitResponse response = recruitService.post(addRecruitRequest);
        //than
        assertEquals("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", response.getContent(), "입력한 내용이 정확해야한다.");
        assertEquals(1000000L, response.getReward(), "채용보상금이 정확해야 한다.");
        assertEquals("백엔드 주니어 개발자", response.getPosition(), "채용포지션이 정확해야 한다.");
        assertEquals("Python", response.getSkills(), "사용기술이 정확해야 한다.");
        assertEquals("원티드", response.getCompanyName(), "회사명이 정확해야 한다.");
    }

}