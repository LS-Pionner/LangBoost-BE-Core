package com.example.memoria.service;

import com.example.memoria.dto.ReferenceRequestDto;
import com.example.memoria.dto.ReferenceResponseDto;
import com.example.memoria.entity.Reference;
import com.example.memoria.entity.Sentence;
import com.example.memoria.repository.ReferenceRepository;
import com.example.memoria.repository.SentenceRepository;
import com.example.memoria.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.api.response.CustomException;

@Service
@RequiredArgsConstructor
public class ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final SentenceRepository sentenceRepository;

    public ReferenceResponseDto getReferenceById(Long referenceId) {
        Reference reference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_REFERENCE));

        return new ReferenceResponseDto(reference);
    }

    public ReferenceResponseDto createReference(ReferenceRequestDto requestDto) {

        Sentence sentence = sentenceRepository.findById(requestDto.sentenceId())
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        // reference가 이미 존재하는지 확인
        referenceRepository.findBySentenceId(requestDto.sentenceId())
                .ifPresent(existingReference -> {
                    throw new CustomException(ErrorCode.REFERENCE_ALREADY_EXISTS);
                });

        Reference reference = Reference.builder()
                .url(requestDto.url())
                .sentence(sentence)
                .build();

        Reference savedReference = referenceRepository.save(reference);
        return new ReferenceResponseDto(savedReference);
    }

    public ReferenceResponseDto updateReference(Long referenceId, ReferenceRequestDto requestDto) {
        // 기존 Reference 조회
        Reference reference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_REFERENCE));

        // Reference 업데이트
        reference.updateReference(requestDto.url());

        // 업데이트된 Reference 저장
        Reference updatedReference = referenceRepository.save(reference);

        return new ReferenceResponseDto(updatedReference);
    }

    public void deleteReference(Long referenceId) {
        Reference reference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_REFERENCE));
        referenceRepository.delete(reference);
    }

    // sentenceId로 Reference 조회
    public ReferenceResponseDto getReferenceBySentenceId(Long sentenceId) {
        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        Reference reference = referenceRepository.findBySentenceId(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_REFERENCE));

        return new ReferenceResponseDto(reference);
    }

}
