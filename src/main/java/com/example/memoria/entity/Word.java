package com.example.memoria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다른 MSA 서버의 사용자 ID
    @Column(name = "user_id", nullable = false)
    private Long userId; // 외래 키는 아니지만, 다른 MSA 시스템의 ID를 저장

    @ManyToOne
    @JoinColumn(name = "sentence_id", nullable = false)
    private Sentence sentence;

    @Column(nullable = false)
    private String word;

    private String description;

//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDate lastViewedDate;

    // 업데이트 할 때 사용
    public void updateWord(String newWord, String newDescription) {
        this.word = newWord;
        this.description = newDescription;
    }

}