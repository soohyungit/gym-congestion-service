package com.gym.congestion.service;

import com.gym.congestion.entity.Gym;
import com.gym.congestion.entity.User;
import com.gym.congestion.entity.VisitLog;
import com.gym.congestion.exception.CheckInNotFoundException;  // 추가
import com.gym.congestion.exception.GymFullException;
import com.gym.congestion.exception.GymNotFoundException;      // 추가
import com.gym.congestion.exception.UserNotFoundException;     // 추가
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

    @Transactional
    public void checkIn(Long userId, Long gymId) {
        // 변경 전: userRepository.findById(userId).orElseThrow();
        // 변경 후: 명확한 예외 메시지
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // ⭐ 일반 findById 대신 락이 걸린 메서드 사용!
        Gym gym = gymRepository.findByIdWithLock(gymId)
                .orElseThrow(() -> new GymNotFoundException(gymId));
        // [추가] 정원 초과 여부 확인
        if (gym.getCurrentCount() >= gym.getMaxCapacity()) {
            throw new GymFullException(gymId);
        }
        gym.updateCurrentCount(gym.getCurrentCount() + 1);
        visitLogRepository.save(VisitLog.builder()
                .user(user)
                .gym(gym)
                .checkInAt(LocalDateTime.now())
                .build());
    }

    @Transactional
    public void checkOut(Long userId, Long gymId) {
        // 변경 전: .orElseThrow(() -> new IllegalStateException("입장 기록이 없습니다."));
        // 변경 후: 어떤 사용자, 어떤 헬스장인지 명확
        VisitLog log = visitLogRepository
                .findFirstByUserIdAndGymIdAndCheckOutAtIsNullOrderByCheckInAtDesc(userId, gymId)
                .orElseThrow(() -> new CheckInNotFoundException(userId, gymId));

        // ⭐ 퇴장할 때도 자물쇠를 걸어서 '수정 권한'을 독점해야 해!
        Gym gym = gymRepository.findByIdWithLock(gymId)
                .orElseThrow(() -> new GymNotFoundException(gymId));

        gym.updateCurrentCount(gym.getCurrentCount() - 1);
        log.recordCheckOut();
    }
}