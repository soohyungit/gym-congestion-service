package com.gym.congestion.controller;

import com.gym.congestion.entity.Gym;
import com.gym.congestion.repository.GymRepository;
import com.gym.congestion.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON 형태로 데이터를 반환하는 컨트롤러임을 선언해
@RequestMapping("/api/gyms") // 이 주소로 들어오는 요청을 여기서 처리해
@RequiredArgsConstructor // Repository 주입을 위해 사용해
public class GymController {

    private final GymRepository gymRepository;
    private final GymService gymService;

    // 모든 헬스장의 혼잡도 정보를 가져오는 API
    @GetMapping
    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    // @PostMapping("/check-in") -> 이 줄을 아래처럼 바꿔줘
    @GetMapping("/check-in")
    public String checkIn(@RequestParam Long userId, @RequestParam Long gymId) {
        gymService.checkIn(userId, gymId);
        return "입장이 완료되었습니다!";
    }
    @GetMapping("/check-out")
    public String checkOut(@RequestParam Long userId, @RequestParam Long gymId) {
        gymService.checkOut(userId, gymId);
        return "퇴장이 완료되었습니다. 안녕히 가세요!";
    }
}