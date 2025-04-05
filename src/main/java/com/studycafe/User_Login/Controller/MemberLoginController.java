package com.studycafe.User_Login.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberLoginController {
    //로그인 화면으로 이동
    @GetMapping("/user/login")
    public String login() {
        return "user_login";
    }
}
