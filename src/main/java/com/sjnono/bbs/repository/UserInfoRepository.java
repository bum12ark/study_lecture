package com.sjnono.bbs.repository;

import com.sjnono.bbs.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    public Optional<UserInfo> findByEmail(String email);
}
