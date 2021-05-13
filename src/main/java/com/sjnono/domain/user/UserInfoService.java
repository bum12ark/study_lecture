package com.sjnono.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
