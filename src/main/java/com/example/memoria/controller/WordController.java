package com.example.memoria.controller;

import com.example.memoria.dto.WordRequestDto;
import com.example.memoria.dto.WordResponseDto;
import com.example.memoria.response.ApiResponse;
import com.example.memoria.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WordController {

    private final WordService wordService;

    // 단어 1개 조회
    @GetMapping("/word/{wordId}")
    public ApiResponse<WordResponseDto> getWordById(@PathVariable Long wordId) {
        WordResponseDto word = wordService.getWordById(wordId);
        return ApiResponse.ok(word);
    }

    @PostMapping("/word")
    public ApiResponse<WordResponseDto> createWord(@RequestBody WordRequestDto requestDto) {
        WordResponseDto createdWord = wordService.createWord(requestDto);
        return ApiResponse.ok(createdWord);
    }

    @PutMapping("/word/{wordId}")
    public ApiResponse<WordResponseDto> updateWord(@PathVariable Long wordId, @RequestBody WordRequestDto requestDto) {
        WordResponseDto updatedWord = wordService.updateWord(wordId, requestDto);
        return ApiResponse.ok(updatedWord);
    }

    @DeleteMapping("/word/{wordId}")
    public ApiResponse<String> deleteWord(@PathVariable Long wordId) {
        wordService.deleteWord(wordId);
        return ApiResponse.ok("단어 삭제 성공");
    }


    // GET 요청: sentenceId로 문장에 속한 단어들을 리스트로 반환
    @GetMapping("/words/{sentenceId}")
    public ApiResponse<List<WordResponseDto>> getWordsBySentenceId(@PathVariable Long sentenceId) {
        List<WordResponseDto> words = wordService.getWordsBySentenceId(sentenceId);
        return ApiResponse.ok(words);
    }
}
