package com.gym.congestion.exception;

public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(Long userId, Long gymId) {
        super(String.format("입장 기록을 찾을 수 없습니다. (사용자 ID: %d, 헬스장 ID: %d)", userId, gymId));
    }
}