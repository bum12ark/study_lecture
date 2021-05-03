package com.sjnono.domain.user;

import com.sjnono.domain.user.UserInfo;
import com.sjnono.domain.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    public UserInfo save(UserInfo userInfo) {
        UserInfo newUserInfo = userInfoRepository.save(userInfo);
        return newUserInfo;
    }

    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email)
                .orElseThrow(NullPointerException::new);
    }
}
