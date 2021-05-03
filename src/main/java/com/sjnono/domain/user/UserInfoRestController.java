package com.sjnono.domain.user;


import com.sjnono.global.common.ApiResponseMessage;
import com.sjnono.global.common.StatusEnum;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserInfoRestController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoRestController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseMessage> login(@RequestBody UserInfoDto userInfoDto) {
        ResponseEntity<ApiResponseMessage> entity = null;
        ApiResponseMessage apiResponseMessage = null;

        try {
            UserInfo param = modelMapper.map(userInfoDto, UserInfo.class);

            UserInfo findUserInfo = userInfoService.findByEmail(param.getEmail());

            if (param.getPassword() == findUserInfo.getPassword()) {
                apiResponseMessage = ApiResponseMessage.builder()
                        .status(StatusEnum.OK).message("성공 코드").data(findUserInfo)
                        .build();
            } else {
                apiResponseMessage = ApiResponseMessage.builder()
                        .status(StatusEnum.ALERT).message("계정 정보가 일치하지 않습니다.")
                        .build();
            }

            entity = new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
        } catch (NullPointerException ne) {
            apiResponseMessage = ApiResponseMessage.builder()
                    .status(StatusEnum.ALERT).message("계정 정보가 일치하지 않습니다.")
                    .build();
            entity = new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            apiResponseMessage = ApiResponseMessage.builder()
                    .status(StatusEnum.INTERNAL_SERER_ERROR).message("에러코드")
                    .build();
            entity = new ResponseEntity<>(apiResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);

            e.printStackTrace();
        }

        return entity;
    }
}
