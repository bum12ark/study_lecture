package com.sjnono.domain.user;

import com.sjnono.global.common.CustomException;
import com.sjnono.global.common.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo save(UserInfo userInfo) {
        UserInfo newUserInfo = userInfoRepository.save(userInfo);
        return newUserInfo;
    }

    public Optional<UserInfo> findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    public void login() throws Exception{
        UserInfoDto userInfoDto = UserInfoDto.builder().email("test@naver.com").name("이름").build();

        throw new CustomException(new ErrorResponse("테스트 메시지", userInfoDto, "join"));
    }
}
