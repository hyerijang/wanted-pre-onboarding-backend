package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitSimpleDto {

    @NotNull
    private Long id; //채용공고 id
    @NotBlank

    private String position;
    @NotNull

    private Long reward;
    @NotBlank

    private String content;
    @NotBlank
    private String skills;
    @NotBlank
    private String companyName;

    @Builder
    public RecruitSimpleDto(Recruit recruit) {
        this.id = recruit.getId();
        this.position = recruit.getPosition();
        this.reward = recruit.getReward();
        this.content = recruit.getContent();
        this.skills = recruit.getSkills();
        this.companyName = recruit.getCompany().getName();
    }

}
