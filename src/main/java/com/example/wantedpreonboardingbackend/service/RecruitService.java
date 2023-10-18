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

    /***
     * 채용공고 등록
     * @param addRecruitRequest
     * @return
     */
    @Transactional
    public AddRecruitResponse post(AddRecruitRequest addRecruitRequest) {
        Company company = companyRepository.findOne(addRecruitRequest.getCompanyId()); //회사 서비스에 메소드 만들고 그 안에서 유효성체크도 해야할듯
        Recruit recruit = addRecruitRequest.toEntity(company);
        recruitRepository.save(recruit);
        return new AddRecruitResponse(recruit);
    }

    /***
     * 채용공고 수정, 삭제된 공고는 수정할 수 없다
     * @param id
     * @param editRecruitRequest
     * @return
     */
    @Transactional
    public EditRecruitResponse edit(Long id, EditRecruitRequest editRecruitRequest) {
        Recruit recruit = recruitRepository.findOne(id);
        validationCheck(recruit); //삭제된 공고는 수정 불가함
        recruit.editRecruit(editRecruitRequest);
        return new EditRecruitResponse(recruit);
    }


    /***
     * 채용공고 삭제
     * @param id
     * @return DeleteRecruitResponse는 공고 id와 삭제 상태를 포함한다.
     */
    @Transactional
    public DeleteRecruitResponse delete(Long id) {
        Recruit recruit = recruitRepository.findOne(id);
        validationCheck(recruit);
        recruit.deleteRecruit(); //soft delete
        return new DeleteRecruitResponse(recruit);
    }

    /**
     * 존재하거나 삭제된 공고라면 예외가 발생한다.
     * @param recruit
     */
    private static void validationCheck(Recruit recruit) {
        if (recruit == null) {
            throw new NullPointerException("존재하지 않는 공고의 id 입니다.");
        }
        if (recruit.isDeleted()) {
            throw new NullPointerException("삭제 된 공고입니다.");
        }
    }

    /**
     * 전체 채용 공고 조회 - 삭제된 공고는 제외
     */
    public List<RecruitDto> findRecruits(int offset, int limit) {
        List<Recruit> recruits = recruitRepository.findAll(offset, limit);
        //DTO 변환
        List<RecruitDto> collect = recruits.stream()
                .map(r -> RecruitDto.builder().recruit(r).build())
                .collect(Collectors.toList());
        return collect;
    }

    /***
     * 채용공고 단건 조회
     * @param id
     * @return
     */
    public RecruitDetailDto detail(Long id) {
        Recruit recruit = recruitRepository.findOne(id);
        validationCheck(recruit);
        return new RecruitDetailDto(recruit);
    }
}
