package com.example.memoria.repository;

import com.example.memoria.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    Optional<Reference> findBySentenceId(Long sentenceId);
}
