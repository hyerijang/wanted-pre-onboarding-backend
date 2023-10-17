package com.example.wantedpreonboardingbackend.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "recruit")
@NoArgsConstructor
public class Recruit {

    @Id
    @GeneratedValue
    @Column(name = "recruit_id")
    private Long id;
    private String position;
    private Long reward;
    private String content;
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
}
