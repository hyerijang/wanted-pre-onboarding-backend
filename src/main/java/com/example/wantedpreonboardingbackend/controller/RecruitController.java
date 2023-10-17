package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.dto.AddRecruitRequest;
import com.example.wantedpreonboardingbackend.dto.AddRecruitResponse;
import com.example.wantedpreonboardingbackend.service.RecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    //1.채용공고 등록
    @PostMapping()
    public AddRecruitResponse addRecruit(AddRecruitRequest addRecruitRequest) {
        return recruitService.post(addRecruitRequest);
    }

}

