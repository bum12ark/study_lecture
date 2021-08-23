package com.sjnono.domain.user;


import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping("/user")
@RestController
public class UserInfoRestController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoRestController.class);

    private final UserInfoService userInfoService;

    private final ModelMapper modelMapper;

    private final UserInfoValidator userInfoValidator;

    public UserInfoRestController(UserInfoService userInfoService, ModelMapper modelMapper, UserInfoValidator userInfoValidator) {
        this.userInfoService = userInfoService;
        this.modelMapper = modelMapper;
        this.userInfoValidator = userInfoValidator;
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity findUserByEmail(@PathVariable String email) {
        UserInfo findUserInfo = this.userInfoService.findByEmail(email);

        EntityModel<UserInfo> model = EntityModel.of(findUserInfo)
                .add(linkTo(UserInfoRestController.class).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @PostMapping(value = "")
    public ResponseEntity createUser(@RequestBody UserInfoDto userInfoDto) {
        userInfoValidator.joinValidator(userInfoDto);

        //UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);
        UserInfo userInfo = UserInfo.builder()
                .email(userInfoDto.getEmail())
                .name(userInfoDto.getName())
                .password(userInfoDto.getPassword())
                .build();

        UserInfo createdUserInfo = this.userInfoService.save(userInfo);

        EntityModel<UserInfo> model = EntityModel.of(createdUserInfo);
        Link link = linkTo(UserInfoRestController.class).withSelfRel();
        model.add(link);

        ResponseEntity<EntityModel<UserInfo>> entity = ResponseEntity.created(link.toUri()).body(model);
        return entity;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@RequestBody UserInfoDto userInfoDto, Errors errors) throws Exception {
        userInfoValidator.loginValidator(userInfoDto, errors);

        if (errors.hasErrors()) {
            // http://localhost/user/join
            EntityModel<Errors> model = EntityModel.of(errors)
                    .add(linkTo(this.getClass()).slash("join").withSelfRel());
            return ResponseEntity.badRequest().body(model);
        }

        UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);

        userInfoService.login();

        // Optional<UserInfo> optional = this.userInfoService.findByEmail(userInfo.getEmail());
        return ResponseEntity.ok(linkTo(this.getClass()).withSelfRel());
    }
}
