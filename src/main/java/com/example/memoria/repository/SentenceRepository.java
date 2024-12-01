package com.example.memoria.repository;

import com.example.memoria.entity.Sentence;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
    // userId를 기준으로 Sentence를 찾고 페이지네이션 적용
    Page<Sentence> findByUserId(Long userId, Pageable pageable);
}