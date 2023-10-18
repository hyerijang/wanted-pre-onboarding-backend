package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.dto.*;
import com.example.wantedpreonboardingbackend.service.RecruitService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    //1.채용공고 등록
    @PostMapping()
    public AddRecruitResponse addRecruit(@RequestBody AddRecruitRequest addRecruitRequest) {
        return recruitService.post(addRecruitRequest);
    }

    //2.채용공고 수정
    @PatchMapping("/{id}")
    public EditRecruitResponse addRecruit(@PathVariable Long id, @RequestBody EditRecruitRequest editRecruitRequest) {
        return recruitService.edit(id, editRecruitRequest);
    }

    //3.채용공고 삭제
    @DeleteMapping("/{id}")
    public DeleteRecruitResponse removeRecruit(@PathVariable Long id) {
        return recruitService.delete(id);
    }


    //4.채용공고 목록 조회
    @GetMapping()
    public Result recruits(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<RecruitDto> result = recruitService.findRecruits(offset, limit);
        log.info("size = {}", result.size());
        return new Result(result);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }

    //5. 채용 상세 페이지 가져오기
    @GetMapping("/{id}")
    public RecruitDetailDto detail(@PathVariable Long id) {


        RecruitDetailDto detail = recruitService.detail(id);
        return detail;

    }


}

