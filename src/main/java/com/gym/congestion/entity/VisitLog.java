package com.gym.congestion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N:1 관계 설정 (여러 로그는 하나의 유저에게 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // N:1 관계 설정 (여러 로그는 하나의 헬스장에 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private LocalDateTime checkInAt; // 입장 시간

    private LocalDateTime checkOutAt; // 퇴장 시간 (퇴장 전에는 null)

    @Builder
    public VisitLog(User user, Gym gym, LocalDateTime checkInAt) {
        this.user = user;
        this.gym = gym;
        this.checkInAt = checkInAt;
    }

    // 퇴장 시 시간을 기록하는 메서드
    public void recordCheckOut() {
        this.checkOutAt = LocalDateTime.now();
    }
}