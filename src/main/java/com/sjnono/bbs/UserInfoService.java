package com.sjnono.bbs;

import com.sjnono.bbs.entity.UserInfo;
import com.sjnono.bbs.repository.UserInfoRepository;
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
