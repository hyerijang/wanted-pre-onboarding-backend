package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditRecruitRequest {
    @NotBlank
    private String position;
    @NotNull

    private Long reward;
    @NotBlank
    private String content;
    @NotBlank

    private String skills;


    public Recruit toEntity() {
        return Recruit.builder()
                .position(position)
                .reward(reward)
                .content(content)
                .skills(skills)
                .build();
    }
}
