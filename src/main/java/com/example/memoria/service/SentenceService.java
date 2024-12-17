package com.example.memoria.service;

import com.example.memoria.dto.PagingResponseDto;
import com.example.memoria.dto.Passport;
import com.example.memoria.dto.SentenceRequestDto;
import com.example.memoria.dto.SentenceResponseDto;
import com.example.memoria.entity.Sentence;
import com.example.memoria.events.SentenceViewedEvent;
import com.example.memoria.repository.SentenceRepository;
import com.example.memoria.response.ErrorCode;
import com.example.memoria.util.DateUtil;
import com.example.memoria.util.PassportUtil;
import com.example.memoria.util.RequestContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;


import com.example.api.response.CustomException;


import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class SentenceService {

    private final SentenceRepository sentenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SentenceResponseDto getSentenceById(Long sentenceId) {
        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        // 문장 조회 후 최근 조회 이벤트 발생
        eventPublisher.publishEvent(new SentenceViewedEvent(sentenceId));

        return new SentenceResponseDto(sentence);
    }

    public SentenceResponseDto createSentence(SentenceRequestDto requestDto) {
        Long userId = PassportUtil.getUserId();

        Sentence sentence = Sentence.builder()
                .sentence(requestDto.sentence())
                .description(requestDto.description())
                .lastViewedDate(DateUtil.getLastViewedDate())
                .userId(userId)
                .build();

        Sentence savedSentence = sentenceRepository.save(sentence);

        return new SentenceResponseDto(savedSentence);
    }

    public SentenceResponseDto updateSentence(Long sentenceId, SentenceRequestDto requestDto) {
        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        sentence.updateSentence(requestDto.sentence(), requestDto.description());

        Sentence updatedSentence = sentenceRepository.save(sentence);

        return new SentenceResponseDto(updatedSentence);
    }

    public void deleteSentence(Long sentenceId) {
        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));
        sentenceRepository.delete(sentence);
    }

    // sentence 페이징
    public PagingResponseDto<SentenceResponseDto> getSentencesPage(int page) {

        Long userId = PassportUtil.getUserId();

        // 10개 페이징
        Pageable pageable = PageRequest.of(page, 10);

        Page<Sentence> sentencePage = sentenceRepository.findByUserId(userId, pageable);
        Page<SentenceResponseDto> sentencesPage = sentencePage.map(SentenceResponseDto::new);

        return PagingResponseDto.of(sentencesPage);
    }

}
