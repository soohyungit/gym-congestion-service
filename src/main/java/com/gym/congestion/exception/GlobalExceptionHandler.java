package com.gym.congestion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // UserNotFoundException이 발생하면 이 메서드가 실행됨
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // GymNotFoundException이 발생하면 이 메서드가 실행됨
    @ExceptionHandler(GymNotFoundException.class)
    public ResponseEntity<String> handleGymNotFound(GymNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    // 정원 초과시 발생할 메세지
    @ExceptionHandler(GymFullException.class)
    public ResponseEntity<String> handleGymFull(GymFullException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(CheckInNotFoundException.class)
    public ResponseEntity<String> handleCheckInNotFound(CheckInNotFoundException e) {
        // 400(Bad Request)이나 404(Not Found)를 사용하는 것이 적절해
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}