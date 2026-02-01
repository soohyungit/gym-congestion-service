package com.gym.congestion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성을 막기 위해!
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 헬스장 이름

    private String location; // 위치

    private Integer maxCapacity; // 최대 수용 인원

    private Integer currentCount; // 현재 인원

    @Builder // 안전하게 객체를 생성하기 위해 사용해
    public Gym(String name, String location, Integer maxCapacity, Integer currentCount) {
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentCount = currentCount;
    }
    // Gym.java 파일 안에 추가

    public String getCongestionStatus() {
        if (this.maxCapacity == null || this.maxCapacity == 0) return "정보 없음";

        double ratio = (double) this.currentCount / this.maxCapacity * 100;

        if (ratio < 30) return "여유";
        if (ratio < 70) return "보통";
        return "혼잡";
    }

    // 인원수를 변경하는 비즈니스 로직 (나중에 컨트롤러에서 쓸 거야)
    public void updateCurrentCount(Integer count) {
        // 0보다 작은 값이 들어오려고 하면 강제로 0으로 고정!
        if (count < 0) {
            this.currentCount = 0;
        } else {
            this.currentCount = count;
        }
    }
}