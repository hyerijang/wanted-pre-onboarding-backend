package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.domain.Recruit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRecruitResponse {

    @NotNull
    private Long id;

    @NotNull
    private boolean isDeleted;

    @Builder
    public DeleteRecruitResponse(Recruit recruit) {
        this.id = recruit.getId();
        this.isDeleted = recruit.isDeleted();
    }
}
