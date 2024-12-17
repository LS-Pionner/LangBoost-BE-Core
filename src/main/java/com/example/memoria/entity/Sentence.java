package com.example.memoria.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 다른 MSA 서버의 사용자 ID
    @Column(name = "user_id", nullable = false)
    private Long userId; // 외래 키는 아니지만, 다른 MSA 시스템의 ID를 저장

    @Column(nullable = false)
    private String sentence;

    private String description;

//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime creationDate;

    @Column(nullable = false)
    private LocalDate lastViewedDate;

    @OneToMany(mappedBy = "sentence", cascade = CascadeType.ALL)
    private List<Word> words;

    @OneToOne(mappedBy = "sentence", cascade = CascadeType.ALL)
    private Reference reference;

    // 업데이트 메서드 추가
    public void updateSentence(String newSentence, String newDescription) {
        this.sentence = newSentence;
        this.description = newDescription;
    }

    public void updateLastViewedDate(LocalDate newLastViewedDate) {
        this.lastViewedDate = newLastViewedDate;
    }

}

