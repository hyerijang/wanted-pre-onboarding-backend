package com.example.wantedpreonboardingbackend.domain;


import com.example.wantedpreonboardingbackend.dto.EditRecruitRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@Table(name = "recruit")
@NoArgsConstructor
public class Recruit {

    @Id
    @GeneratedValue
    @Column(name = "recruit_id")
    private Long id;
    @NotBlank
    private String position;
    @NotNull
    private Long reward;
    @NotBlank

    private String content;
    @NotBlank
    private String skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Recruit(Long id, String position, Long reward, String content, String skills, Company company) {
        this.id = id;
        this.position = position;
        this.reward = reward;
        this.content = content;
        this.skills = skills;
        setCompany(company);
    }

    public void setCompany(Company company) {
        this.company = company;
        company.getRecruits().add(this);
    }

    public void editRecruit(EditRecruitRequest editRecruitRequest) {
        //회사id 이외 모두 수정 가능
        this.position = editRecruitRequest.getPosition();
        this.reward = editRecruitRequest.getReward();
        this.content = editRecruitRequest.getContent();
        this.skills = editRecruitRequest.getSkills();
    }
}
