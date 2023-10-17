package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditRecruitResponse {
    @NotBlank
    private String position;
    @NotNull

    private Long reward;
    @NotBlank

    private String content;
    @NotBlank
    private String skills;

    @Builder
    public EditRecruitResponse(Recruit recruit) {
        this.position = recruit.getPosition();
        this.reward = recruit.getReward();
        this.content = recruit.getContent();
        this.skills = recruit.getSkills();
    }
}
