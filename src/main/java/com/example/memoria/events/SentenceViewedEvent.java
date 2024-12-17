package com.example.memoria.events;

public class SentenceViewedEvent {
    private final Long sentenceId;

    public SentenceViewedEvent(Long sentenceId) {
        this.sentenceId = sentenceId;
    }

    public Long getSentenceId() {
        return sentenceId;
    }
}

