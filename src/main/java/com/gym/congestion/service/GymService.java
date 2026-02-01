package com.gym.congestion.service;

import com.gym.congestion.entity.Gym;
import com.gym.congestion.entity.User;
import com.gym.congestion.entity.VisitLog;
import com.gym.congestion.repository.GymRepository;
import com.gym.congestion.repository.UserRepository;
import com.gym.congestion.repository.VisitLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;
    private final UserRepository userRepository;
    private final VisitLogRepository visitLogRepository;

    @Transactional // ⭐ 매우 중요: 이 과정 중 하나라도 실패하면 전부 취소해줘!
    public void checkIn(Long userId, Long gymId) {
        // 1. 유저와 헬스장 정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 헬스장입니다."));

        // 2. 헬스장 현재 인원수 +1 증가
        gym.updateCurrentCount(gym.getCurrentCount() + 1);

        // 3. 방문 로그 생성 및 저장
        VisitLog log = VisitLog.builder()
                .user(user)
                .gym(gym)
                .checkInAt(LocalDateTime.now())
                .build();

        visitLogRepository.save(log);
    }
    // GymService.java에 추가
    @Transactional
    public void checkOut(Long userId, Long gymId) {
        // 1. 해당 유저가 해당 헬스장에 입장해서 아직 안 나간 로그를 찾음
        VisitLog log = visitLogRepository.findFirstByUserIdAndGymIdAndCheckOutAtIsNullOrderByCheckInAtDesc(userId, gymId)
                .orElseThrow(() -> new IllegalArgumentException("입장 기록이 없는 사용자입니다."));

        // 2. 헬스장 정보 가져오기
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 헬스장입니다."));

        // 3. 인원수 -1 감소 (0보다 작아지지 않게 로직을 넣으면 더 좋아!)
        if (gym.getCurrentCount() > 0) {
            gym.updateCurrentCount(gym.getCurrentCount() - 1);
        }

        // 4. 퇴장 시간 기록 (아까 엔티티에 만든 메서드 호출)
        log.recordCheckOut();
    }
}