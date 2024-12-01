package com.example.memoria.repository;

import com.example.memoria.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findBySentenceId(Long sentenceId);  // 문장 ID로 단어 목록 조회
}
