package com.example.memoria.events;

import com.example.memoria.repository.SentenceRepository;
import com.example.memoria.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@AllArgsConstructor
public class SentenceViewedEventListener {

    private final SentenceRepository sentenceRepository;

    @Async
    @EventListener
    @Transactional
    // 문장 조회 이벤트가 발생한다면 현재 날짜로 업데이트
    public void handleSentenceViewedEvent(SentenceViewedEvent event) {
        sentenceRepository.findById(event.getSentenceId()).ifPresent(sentence -> {
            sentence.updateLastViewedDate(DateUtil.getLastViewedDate());
            sentenceRepository.save(sentence);
        });
    }
}

