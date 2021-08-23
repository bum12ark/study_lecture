package com.sjnono.domain.user;

import com.sjnono.global.error.exception.BadRequestException;
import com.sjnono.global.error.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(linkTo(UserInfoRestController.class).withSelfRel()));
    }

    public void login() {

        Link link = linkTo(UserInfoRestController.class).slash("join").withSelfRel();
        throw new BadRequestException(link);
        //throw new NotFoundException(link);
    }
}
