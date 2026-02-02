package com.gym.congestion.exception;

public class GymNotFoundException extends RuntimeException {
    public GymNotFoundException(Long id) {
        super("헬스장을 찾을 수 없습니다. ID: " + id);
    }
}