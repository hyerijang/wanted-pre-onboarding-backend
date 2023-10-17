package com.example.wantedpreonboardingbackend.dto;

import com.example.wantedpreonboardingbackend.dto.AddRecruitRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeanValidationTest {
    private AddRecruitRequest createFullFilledAddRecruitRequest() {
        // 검증을 만족하는 request
        AddRecruitRequest addRecruitRequest = new AddRecruitRequest();
        addRecruitRequest.setContent("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        addRecruitRequest.setReward(1000000L);
        addRecruitRequest.setPosition("백엔드 주니어 개발자");
        addRecruitRequest.setSkills("Python");
        addRecruitRequest.setCompanyId(98765L);
        return addRecruitRequest;
    }

    @Test
    void validation_통과() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(violations.isEmpty());
        validatorFactory.close();
    }



    @Test
    void 회사ID는_null일_수_없다() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        request.setCompanyId(null); //회사id null
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(!violations.isEmpty());
        validatorFactory.close();
    }

    @Test
    void 채용리워드는_null일_수_없다() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        request.setReward(null); //회사id null
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(!violations.isEmpty());
        validatorFactory.close();
    }


    @Test
    void 내용은_empty일_수_없다() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        request.setContent(""); // 내용 empty
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(!violations.isEmpty());
        validatorFactory.close();
    }

    @Test
    void 채용포지션은_empty일_수_없다() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        request.setPosition(""); // 채용포지션 empty
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(!violations.isEmpty());
        validatorFactory.close();
    }

    @Test
    void 사용기술은_empty일_수_없다() {
        //given
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        AddRecruitRequest request = createFullFilledAddRecruitRequest();
        request.setSkills(""); // 사용기술 empty
        //when
        Set<ConstraintViolation<AddRecruitRequest>> violations = validator.validate(request);
        //than
        assertTrue(!violations.isEmpty());
        validatorFactory.close();
    }


}
