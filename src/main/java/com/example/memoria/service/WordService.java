package com.example.memoria.service;

import com.example.memoria.dto.WordRequestDto;
import com.example.memoria.dto.WordResponseDto;
import com.example.memoria.entity.Sentence;
import com.example.memoria.entity.User;
import com.example.memoria.entity.Word;
import com.example.memoria.repository.SentenceRepository;
import com.example.memoria.repository.UserRepository;
import com.example.memoria.repository.WordRepository;
import com.example.memoria.response.CustomException;
import com.example.memoria.response.ErrorCode;
import com.example.memoria.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final UserRepository userRepository;
    private final SentenceRepository sentenceRepository;

    public WordResponseDto getWordById(Long wordId) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_WORD));

        return new WordResponseDto(word);
    }

    public WordResponseDto createWord(WordRequestDto requestDto) {
        Long userId = 1L;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_USER));

        Sentence sentence = sentenceRepository.findById(requestDto.sentenceId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        Word word = Word.builder()
                .word(requestDto.word())
                .description(requestDto.description())
                .lastViewedDate(DateUtil.getLastViewedDate())
                .user(user)
                .sentence(sentence)
                .build();

        Word savedWord = wordRepository.save(word);
        return new WordResponseDto(savedWord);
    }

    public WordResponseDto updateWord(Long wordId, WordRequestDto requestDto) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_WORD));

        word.updateWord(requestDto.word(), requestDto.description());

        Word updatedWord = wordRepository.save(word);

        return new WordResponseDto(updatedWord);
    }

    public void deleteWord(Long wordId) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_WORD));

        wordRepository.delete(word);
    }


    // 문장에 속한 모든 단어 리스트 반환
    public List<WordResponseDto> getWordsBySentenceId(Long sentenceId) {
        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND_SENTENCE));

        List<Word> words = wordRepository.findBySentenceId(sentenceId);
        return words.stream()
                .map(WordResponseDto::new)  // Word -> WordResponseDto 변환
                .collect(Collectors.toList());
    }
}


