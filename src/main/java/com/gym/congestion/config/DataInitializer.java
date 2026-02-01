package com.gym.congestion.config;

import com.gym.congestion.entity.Gym;
import com.gym.congestion.entity.User;
import com.gym.congestion.entity.VisitLog;
import com.gym.congestion.repository.GymRepository;
import com.gym.congestion.repository.UserRepository;
import com.gym.congestion.repository.VisitLogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    // 1. 모든 리포지토리를 선언 (3개)
    private final GymRepository gymRepository;
    private final UserRepository userRepository;
    private final VisitLogRepository visitLogRepository;

    // 2. 생성자 주입을 통해 리포지토리들을 가져옴
    public DataInitializer(GymRepository gymRepository,
                           UserRepository userRepository,
                           VisitLogRepository visitLogRepository) {
        this.gymRepository = gymRepository;
        this.userRepository = userRepository;
        this.visitLogRepository = visitLogRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // --- [1] 헬스장 데이터 생성 및 저장 ---
        Gym gym1 = Gym.builder()
                .name("비타민 휘트니스")
                .location("서울시 강남구")
                .maxCapacity(50)
                .currentCount(11) // 한 명 입장한 상태로 설정
                .build();

        gymRepository.save(gym1);

        // --- [2] 사용자 데이터 생성 및 저장 ---
        User user1 = User.builder()
                .email("test@example.com")
                .password("1234")
                .nickname("헬린이")
                .build();

        userRepository.save(user1);

        // --- [3] 방문 로그 생성 및 저장 (연결 과정) ---
        // 위에서 저장된 gym1과 user1을 그대로 사용하여 로그를 만듦
        VisitLog log = VisitLog.builder()
                .user(user1)   // 어떤 유저가?
                .gym(gym1)     // 어느 헬스장에?
                .checkInAt(LocalDateTime.now()) // 지금 이 시간에!
                .build();

        visitLogRepository.save(log);

        System.out.println("==========================================");
        System.out.println(">> [성공] 헬스장, 유저, 방문로그 데이터 적재 완료!");
        System.out.println("==========================================");
    }
}