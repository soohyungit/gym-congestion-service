package com.gym.congestion.repository;

import com.gym.congestion.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 스프링이 관리하는 저장소라는 뜻이야
public interface GymRepository extends JpaRepository<Gym, Long> {
    // 아무것도 안 적어도 기본적인 저장, 조회, 삭제 기능은 이미 들어있어!
}