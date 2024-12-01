package com.example.memoria.dto;

import com.example.memoria.entity.Sentence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SentenceResponseDto(
        Long id,
        String sentence,
        String description,

//        LocalDateTime creationDate,
        LocalDate lastViewedDate
) {
    public SentenceResponseDto(Sentence sentence) {
        this(
                sentence.getId(),
                sentence.getSentence(),
                sentence.getDescription(),
//                sentence.getCreationDate(),
                sentence.getLastViewedDate()
        );
    }
}

