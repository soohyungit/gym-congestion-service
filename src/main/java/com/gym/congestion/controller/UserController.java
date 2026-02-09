package com.gym.congestion.controller;

import com.gym.congestion.dto.UserJoinRequest;
import com.gym.congestion.dto.UserLoginRequest;
import com.gym.congestion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserJoinRequest request) {
        userService.join(request);
        return "회원가입이 완료되었습니다!";
    }
    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return token; // 브라우저에게 "이게 네 인증서야!" 하고 전달
    }
}