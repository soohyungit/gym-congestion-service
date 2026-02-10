package com.gym.congestion.config;

import com.gym.congestion.entity.Gym;
import com.gym.congestion.entity.User;
import com.gym.congestion.repository.GymRepository;
import com.gym.congestion.repository.UserRepository;
import com.gym.congestion.repository.VisitLogRepository;
import lombok.RequiredArgsConstructor; // 생성자 주입을 위해 추가
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // 추가
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // ⭐ 이 어노테이션을 쓰면 생성자를 직접 안 써도 돼!
public class DataInitializer implements CommandLineRunner {

    private final GymRepository gymRepository;
    private final UserRepository userRepository;
    private final VisitLogRepository visitLogRepository;
    private final PasswordEncoder passwordEncoder; // ⭐ 스프링이 자동으로 주입해줌

    @Override
    public void run(String... args) throws Exception {
        if (gymRepository.count() > 0) return;

        // 1. 헬스장 생성 (0명으로 시작)
        gymRepository.save(Gym.builder().name("비타민 휘트니스").location("서울시 강남구").maxCapacity(50).currentCount(0).build());
        gymRepository.save(Gym.builder().name("애플 헬스장").location("서울시 마포구").maxCapacity(30).currentCount(0).build());

        // 2. 유저 생성 (비밀번호 암호화가 핵심!)
        User user1 = User.builder()
                .email("member@gymservice.com")
                .password(passwordEncoder.encode("password1234")) // ⭐ 암호화 적용!
                .nickname("운동하는대학생")
                .build();
        userRepository.save(user1);

        System.out.println("==========================================");
        System.out.println(">> [성공] 프로젝트 초기 환경 설정 완료!");
        System.out.println(">> 테스트 계정: member@gymservice.com / password1234");
        System.out.println("==========================================");
    }
}