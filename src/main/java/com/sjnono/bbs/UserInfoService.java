package com.sjnono.bbs;

import com.sjnono.bbs.entity.UserInfo;
import com.sjnono.bbs.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    public UserInfo save(UserInfo userInfo) {
        UserInfo newUserInfo = userInfoRepository.save(userInfo);
        return newUserInfo;
    }
}
