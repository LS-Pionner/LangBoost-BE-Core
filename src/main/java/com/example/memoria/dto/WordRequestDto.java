package com.example.memoria.dto;

public record WordRequestDto(
        Long sentenceId,
        String word,
        String description
) {
}
