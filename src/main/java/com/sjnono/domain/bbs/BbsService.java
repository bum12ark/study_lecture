package com.sjnono.domain.bbs;

import com.sjnono.domain.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbsService {
    @Autowired
    private BoardRepository boardRepository;


}
