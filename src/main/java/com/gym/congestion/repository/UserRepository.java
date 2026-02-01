package com.gym.congestion.repository;

import com.gym.congestion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자를 찾는 기능을 추가해볼 수 있어 (로그인 등에 필요함)
    Optional<User> findByEmail(String email);
}