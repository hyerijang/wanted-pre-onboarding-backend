package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import com.example.wantedpreonboardingbackend.dto.AddRecruitRequest;
import com.example.wantedpreonboardingbackend.dto.AddRecruitResponse;
import com.example.wantedpreonboardingbackend.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public AddRecruitResponse post(AddRecruitRequest addRecruitRequest) {
        Company company = companyRepository.findOne(addRecruitRequest.getCompanyId()); // 존재하는 회사인지 체크
        Recruit recruit = addRecruitRequest.toEntity(company);
        recruitRepository.save(recruit);
        return new AddRecruitResponse(recruit);
    }
}
