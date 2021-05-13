package com.sjnono.domain.user;


import com.sjnono.global.common.ApiResponseMessage;
import com.sjnono.global.common.StatusEnum;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        Optional<UserInfo> optional = this.userInfoService.findByEmail(email);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<UserInfo> model = EntityModel.of(optional.get());
        model.add(linkTo(UserInfoRestController.class).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createUser(@RequestBody UserInfoDto userInfoDto, Errors errors) {
        userInfoValidator.joinValidator(userInfoDto, errors);

        if (errors.hasErrors()) {
            // http://localhost/user/join
            EntityModel<Errors> model = EntityModel.of(errors)
                    .add(linkTo(this.getClass()).slash("join").withSelfRel());
            return ResponseEntity.badRequest().body(model);
        }

        UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);

        UserInfo createdUserInfo = this.userInfoService.save(userInfo);

        EntityModel<UserInfo> model = EntityModel.of(createdUserInfo);
        Link link = linkTo(UserInfoRestController.class).withSelfRel();
        model.add(link);


        ResponseEntity<EntityModel<UserInfo>> entity = ResponseEntity.created(link.toUri()).body(model);
        return entity;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@RequestBody UserInfoDto userInfoDto, Errors errors) {
        userInfoValidator.loginValidator(userInfoDto, errors);

        if (errors.hasErrors()) {
            // http://localhost/user/join
            EntityModel<Errors> model = EntityModel.of(errors)
                    .add(linkTo(this.getClass()).slash("join").withSelfRel());
            return ResponseEntity.badRequest().body(model);
        }

        UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);

        Optional<UserInfo> optional = this.userInfoService.findByEmail(userInfo.getEmail());

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // TODO 패스워드 비교 리턴 값 어떻게 할지
        UserInfo findByUserInfo = optional.get();
        if (userInfoDto.getPassword().equals(findByUserInfo.getPassword())) {
            return ResponseEntity.ok(linkTo(this.getClass()).withSelfRel());
        } else {

        }


        return ResponseEntity.ok(linkTo(this.getClass()).withSelfRel());
    }
}
