package com.example.memoria.dto.TextToSpeech;

import java.util.List;

public record TtsRequestDto(List<TtsListDto> ttsList) {
    private static final int MAX_LIST_SIZE = 100; // 최대 100개의 TtsListDto만 받기

    public TtsRequestDto {
        if (ttsList.size() > MAX_LIST_SIZE) {
            throw new IllegalArgumentException("The list size exceeds the maximum allowed limit of " + MAX_LIST_SIZE);
        }
    }
}

