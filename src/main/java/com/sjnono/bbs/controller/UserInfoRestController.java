package com.sjnono.bbs.controller;


import com.sjnono.bbs.UserInfoService;
import com.sjnono.bbs.common.ApiResponseMessage;
import com.sjnono.bbs.common.StatusEnum;
import com.sjnono.bbs.entity.UserInfo;
import com.sjnono.bbs.model.UserInfoDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserInfoRestController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoRestController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseMessage> create(@RequestBody UserInfoDto userInfoDto) {
        ResponseEntity<ApiResponseMessage> entity = null;

        try {
            UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);
            UserInfo newUserInfo = userInfoService.save(userInfo);
            ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                    .status(StatusEnum.OK)
                    .message("성공 코드")
                    .data(newUserInfo)
                    .build();
            entity = new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                    .status(StatusEnum.INTERNAL_SERER_ERROR)
                    .message("에러코드")
                    .data(null)
                    .build();
            entity = new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return entity;
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
