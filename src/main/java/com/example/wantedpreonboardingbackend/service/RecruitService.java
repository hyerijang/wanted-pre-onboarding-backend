package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.controller.RecruitController;
import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import com.example.wantedpreonboardingbackend.dto.*;
import com.example.wantedpreonboardingbackend.repository.CompanyRepository;
import com.example.wantedpreonboardingbackend.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Transactional
    public EditRecruitResponse edit(Long id, EditRecruitRequest editRecruitRequest) {
        Recruit recruit = recruitRepository.findOne(id);
        if (recruit == null) {
            throw new NullPointerException("존재하지 않는 공고의 id 입니다.");
        }
        recruit.editRecruit(editRecruitRequest);
        return new EditRecruitResponse(recruit);
    }


    @Transactional
    public DeleteRecruitResponse delete(Long id) {
        Recruit recruit = recruitRepository.findOne(id);
        if (recruit == null) {
            throw new NullPointerException("존재하지 않는 공고의 id 입니다.");
        }
        if (recruit.isDeleted()) {
            throw new NullPointerException("삭제 된 공고입니다.");
        }
        recruit.deleteRecruit();
        return new DeleteRecruitResponse(recruit);
    }

    /**
     * 전체 회원 조회
     */
    public List<RecruitDto> findRecruits(int offset, int limit) {
        List<Recruit> recruits = recruitRepository.findAll(offset, limit);
        //DTO 변환
        List<RecruitDto> collect = recruits.stream()
                .map(r -> RecruitDto.builder().recruit(r).build())
                .collect(Collectors.toList());
        return collect;
    }

}
