package com.example.memoria.dto;

import com.example.memoria.entity.Word;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WordResponseDto(
        Long id,
        Long sentenceId,
        String word,
        String description,

//        LocalDateTime creationDate,  // 생성 날짜
        LocalDate lastViewedDate
) {
    public WordResponseDto(Word word) {
        this(
                word.getId(),
                word.getSentence().getId(),
                word.getWord(),
                word.getDescription(),
//                word.getCreationDate(),
                word.getLastViewedDate()
        );
    }
}


