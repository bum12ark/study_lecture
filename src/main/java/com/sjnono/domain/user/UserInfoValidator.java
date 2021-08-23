package com.sjnono.domain.user;

import com.sjnono.global.error.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserInfoValidator {

    public void joinValidator(UserInfoDto userInfoDto)  {
        if ((userInfoDto.getName() == null || userInfoDto.getName().isBlank())
        || (userInfoDto.getEmail() == null || userInfoDto.getEmail().isBlank())
        || (userInfoDto.getPassword() == null || userInfoDto.getPassword().isBlank())) {
            throw new BadRequestException(linkTo(UserInfoRestController.class).withSelfRel());
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
