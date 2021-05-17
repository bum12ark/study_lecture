package com.sjnono.domain.user;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Component
public class UserInfoValidator {
    public void joinValidator(UserInfoDto userInfoDto, Errors errors) {
        if (userInfoDto.getName() == null || userInfoDto.getName().isBlank()) {
            errors.reject("이름이 비었습니다.");
        } else if (userInfoDto.getEmail() == null || userInfoDto.getEmail().isBlank()) {
            errors.reject("이메일이 비었습니다.");
        } else if (userInfoDto.getPassword() == null || userInfoDto.getPassword().isBlank()) {
            errors.reject("패스워드가 비었습니다.");
        }
    }

    public void loginValidator(UserInfoDto userInfoDto, Errors errors) {
        if (userInfoDto.getEmail() == null || userInfoDto.getEmail().isBlank()) {
            errors.reject("이메일이 비었습니다.");
        } else if (userInfoDto.getPassword() == null || userInfoDto.getPassword().isBlank()) {
            errors.reject("패스워드가 비었습니다.");
        }
    }
}
