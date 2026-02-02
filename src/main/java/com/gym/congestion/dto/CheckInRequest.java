package com.gym.congestion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckInRequest {
    private Long userId;
    private Long gymId;
}