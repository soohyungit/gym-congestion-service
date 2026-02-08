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
        // 1. 데이터가 이미 존재하는지 확인 (헬스장 데이터가 이미 있다면 실행하지 않음)
        if (gymRepository.count() > 0) {
            System.out.println(">> [알림] 이미 초기 데이터가 존재하여 생성을 건너뜁니다.");
            return;
        }

        // --- [1] 첫 번째 헬스장 (비타민 휘트니스 - ID: 1) ---
        Gym gym1 = Gym.builder()
                .name("비타민 휘트니스")
                .location("서울시 강남구")
                .maxCapacity(50)
                .currentCount(11) // 22% -> "여유" 예상
                .build();
        gymRepository.save(gym1);

        // --- [2] 두 번째 헬스장 추가 (애플 헬스장 - ID: 2) ---
        Gym gym2 = Gym.builder()
                .name("애플 헬스장")
                .location("서울시 마포구")
                .maxCapacity(30)
                .currentCount(25) // 83% -> "혼잡" 예상
                .build();
        gymRepository.save(gym2);

        // --- [3] 사용자 데이터 (ID: 1) ---
        User user1 = User.builder()
                .email("test@example.com")
                .password("1234")
                .nickname("헬린이")
                .build();
        userRepository.save(user1);

        // --- [4] 방문 로그 (user1이 gym1에 있는 상태로 시작) ---
        VisitLog log = VisitLog.builder()
                .user(user1)
                .gym(gym1)
                .checkInAt(LocalDateTime.now())
                .build();
        visitLogRepository.save(log);

        System.out.println("==========================================");
        System.out.println(">> [성공] 헬스장 2개, 유저 1명, 방문로그 데이터 적재 완료!");
        System.out.println(">> 비타민 휘트니스 (ID: 1), 애플 헬스장 (ID: 2)");
        System.out.println("==========================================");
    }
}