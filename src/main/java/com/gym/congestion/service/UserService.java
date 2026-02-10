package com.gym.congestion.service;

import com.gym.congestion.config.JwtTokenUtil;
import com.gym.congestion.dto.UserJoinRequest;
import com.gym.congestion.entity.User;
import com.gym.congestion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public Long join(UserJoinRequest request) {
        // 1. 이메일 중복 체크 (현업에서는 필수!)
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화 후 유저 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 암호화!
                .nickname(request.getNickname())
                .build();

        // 3. DB 저장
        return userRepository.save(user).getId();
    }
    public String login(String email, String password) {
        // 1. 이메일로 유저 찾기
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 비교 (암호화된 것과 생으로 들어온 것 비교)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 성공 시 JWT 토큰 발행
        return jwtTokenUtil.createToken(user.getId(), user.getEmail());
    }
}