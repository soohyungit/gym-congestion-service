package com.gym.congestion.repository;

import com.gym.congestion.entity.Gym;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    // 🔒 비관적 락: 데이터를 읽을 때부터 다른 트랜잭션이 건드리지 못하게 잠금을 걸어
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Gym g where g.id = :id")
    Optional<Gym> findByIdWithLock(@Param("id") Long id);
}