package com.example.memoria.dto;

import com.example.memoria.entity.Reference;

public record ReferenceResponseDto(
        Long id,
        Long sentenceId,
        String url
) {
    public ReferenceResponseDto(Reference reference) {
        this(
                reference.getId(),
                reference.getSentence().getId(),
                reference.getUrl()
        );
    }
}

