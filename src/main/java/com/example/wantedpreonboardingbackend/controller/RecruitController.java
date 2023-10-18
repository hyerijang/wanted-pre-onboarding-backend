package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.dto.*;
import com.example.wantedpreonboardingbackend.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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

    @PatchMapping("/{id}")
    public EditRecruitResponse addRecruit(@PathVariable Long id, @RequestBody EditRecruitRequest editRecruitRequest) {
        return recruitService.edit(id, editRecruitRequest);
    }

    @DeleteMapping("/{id}")
    public DeleteRecruitResponse removeRecruit(@PathVariable Long id) {
        return recruitService.delete(id);
    }

}

