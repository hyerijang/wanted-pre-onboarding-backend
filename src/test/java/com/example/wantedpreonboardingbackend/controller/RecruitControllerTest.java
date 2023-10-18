package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.Company;
import com.example.wantedpreonboardingbackend.domain.Recruit;
import com.example.wantedpreonboardingbackend.dto.*;
import com.example.wantedpreonboardingbackend.service.RecruitService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(recruitController).build();
    }

    public Company generateCompany(String name) {
        Company company = new Company();
        company.setId((long) Math.random());
        company.setName(name);
        return company;
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
                .andExpect(jsonPath("companyName").value("원티드"))
                .andDo(MockMvcResultHandlers.print());
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
        Recruit recruit = makeSampleRecruit();
        AddRecruitResponse response = AddRecruitResponse.builder().recruit(recruit).build();
        response.setId(12345L); // 저장 시 ID 생성됨
        return response;
    }

    private Recruit makeSampleRecruit() {
        Recruit recruit = Recruit.builder()
                .skills("Python")
                .position("백엔드 주니어 개발자")
                .reward(1000000L)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .company(generateCompany("원티드"))
                .build();
        return recruit;
    }

    @Test
    void deleteRecruit_success() throws Exception {
        //given
        Long id = (long) Math.random();
        DeleteRecruitResponse response = new DeleteRecruitResponse(makeSampleRecruit());
        response.setId(id);
        response.setDeleted(Boolean.TRUE);
        doReturn(response).when(recruitService).delete(anyLong());
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/recruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("deleted").exists())
                .andExpect(jsonPath("deleted").value(Boolean.TRUE)) // True여야함
                .andDo(MockMvcResultHandlers.print());

    }


    @DisplayName("채용공고 조회")
    @Test
    void getList() throws Exception {
        //given
        List<RecruitDto> response = new ArrayList<>();
        response.add(new RecruitDto(makeSampleRecruit()));
        response.add(new RecruitDto(makeSampleRecruit()));
        response.add(new RecruitDto(makeSampleRecruit()));
        doReturn(response).when(recruitService).findRecruits(0,20);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("offset", "0")
                        .queryParam("limit", "20")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.size()").value(3))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("단건조회")
    @Test
    void findOne() throws Exception {
        //given
        Long id = (long) Math.random();
        RecruitDetailDto response = new RecruitDetailDto(makeSampleRecruit());
        doReturn(response).when(recruitService).detail(id);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recruits/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
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
                .andExpect(jsonPath("companyName").value("원티드"))
                .andDo(MockMvcResultHandlers.print());

    }


}
