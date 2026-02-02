package com.gym.congestion.controller;

import com.gym.congestion.dto.CheckInRequest;
import com.gym.congestion.entity.Gym;
import com.gym.congestion.exception.GymNotFoundException;
import com.gym.congestion.repository.GymRepository;
import com.gym.congestion.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gyms")
@RequiredArgsConstructor
public class GymController {

    private final GymRepository gymRepository;
    private final GymService gymService;

    // 1. [복구됨] 모든 헬스장의 혼잡도 정보를 가져오는 API (조회는 GET!)
    @GetMapping
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    // 2. 입장 테스트 (데이터 변경은 POST!)
    @PostMapping("/check-in")
    public String checkIn(@RequestBody CheckInRequest request) {
        gymService.checkIn(request.getUserId(), request.getGymId());
        return "입장이 완료되었습니다!";
    }

    // 3. 퇴장 테스트 (데이터 변경은 POST!)
    @PostMapping("/check-out")
    public String checkOut(@RequestBody CheckInRequest request) {
        gymService.checkOut(request.getUserId(), request.getGymId());
        return "퇴장이 완료되었습니다. 안녕히 가세요!";
    }
    // GymController.java에 추가
    @GetMapping("/{id}")
    public Gym getGymById(@PathVariable Long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new GymNotFoundException(id)); // 우리가 만든 커스텀 예외 사용!
    }
}