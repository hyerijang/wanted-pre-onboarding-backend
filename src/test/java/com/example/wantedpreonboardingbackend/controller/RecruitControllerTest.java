package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.dto.AddRecruitRequest;
import com.example.wantedpreonboardingbackend.dto.AddRecruitResponse;
import com.example.wantedpreonboardingbackend.service.RecruitService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(MockitoExtension.class)
class RecruitControllerTest {

    @InjectMocks
    private RecruitController recruitController;


    @Mock
    private RecruitService recruitService;

    private MockMvc mockMvc;

    private Company company;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(recruitController).build();
        company = new Company();
        company.setId(1L);
        company.setName("원티드");
    }

    @Test
    void addRecruit() throws Exception {
        //given
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        AddRecruitResponse response = makeAddRecruitResponseOf(request);
        doReturn(response).when(recruitService).post(any(AddRecruitRequest.class));
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/recruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("reward").exists())
                .andExpect(jsonPath("position").exists())
                .andExpect(jsonPath("skills").exists())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("companyName").exists())
                .andExpect(jsonPath("reward").value(1000000L))
                .andExpect(jsonPath("position").value("백엔드 주니어 개발자"))
                .andExpect(jsonPath("skills").value("Python"))
                .andExpect(jsonPath("content").value("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은.."))
                .andExpect(jsonPath("companyName").value("원티드"));
    }

    private AddRecruitRequest createFullFilledAddRecruitRequest() {
        AddRecruitRequest addRecruitRequest = new AddRecruitRequest();
        addRecruitRequest.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        addRecruitRequest.setReward(1000000L);
        addRecruitRequest.setPosition("백엔드 주니어 개발자");
        addRecruitRequest.setSkills("Python");
        addRecruitRequest.setCompanyId(98765L);
        return addRecruitRequest;
    }


    private AddRecruitResponse makeAddRecruitResponseOf(AddRecruitRequest request) {
        AddRecruitResponse response = AddRecruitResponse.builder().recruit(request.toEntity(company)).build();
        response.setId(12345L); // 저장 시 ID 생성됨
        return response;
    }

}
