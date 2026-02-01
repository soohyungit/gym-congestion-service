package com.gym.congestion.repository;

import com.gym.congestion.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    // 특정 유저의 방문 기록만 보고 싶을 때 사용
    List<VisitLog> findByUserId(Long userId);
    Optional<VisitLog> findFirstByUserIdAndGymIdAndCheckOutAtIsNullOrderByCheckInAtDesc(Long userId, Long gymId);
}
