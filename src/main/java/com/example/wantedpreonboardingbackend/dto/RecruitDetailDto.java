package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruitDetailDto {
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
    public RecruitDetailDto(Recruit recruit) {
        this.id = recruit.getId(); //공고 id
        this.position = recruit.getPosition();
        this.reward = recruit.getReward();
        this.content = recruit.getContent();
        this.skills = recruit.getSkills();
        // ====회사정보====
        this.companyName = recruit.getCompany().getName();
    }

}
