package com.gym.congestion.exception;

// exception/GymFullException.java
public class GymFullException extends RuntimeException {
    public GymFullException(Long gymId) {
        super("해당 헬스장(ID: " + gymId + ")은 현재 정원이 꽉 차서 입장이 불가능합니다.");
    }
}