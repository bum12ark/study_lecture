package com.sjnono.domain.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/user")
public class UserInfoController {
    @GetMapping("/join")
    public String join() {
        return "/user/join";
    }

    @GetMapping("/login")
    public String login() { return "/user/login"; }
}
