package com.example.memoria.controller;

import com.example.memoria.dto.PagingResponseDto;
import com.example.memoria.dto.Passport;
import com.example.memoria.dto.SentenceRequestDto;
import com.example.memoria.dto.SentenceResponseDto;
import com.example.memoria.response.ApiResponse;
import com.example.memoria.service.SentenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SentenceController {

    private final SentenceService sentenceService;


    @GetMapping("/sentence/{sentenceId}")
    public ApiResponse<SentenceResponseDto> getSentenceById(@PathVariable Long sentenceId,
                                                            @RequestHeader(value = "x-passport", required = false) String passportHeader,
                                                            @RequestHeader Map<String, String> allHeaders) throws JsonProcessingException {
        // 모든 헤더 로그 출력
        if (!allHeaders.isEmpty()) {
            log.info("Received headers: {}", allHeaders);
        }

        // X-Passport 헤더 로그 출력
        if (passportHeader != null) {
            log.info("Passport header received: {}", passportHeader);

            // JSON 문자열을 Passport 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            Passport passport = objectMapper.readValue(passportHeader, Passport.class);

            log.info("Deserialized Passport object: {}", passport);
        } else {
            log.warn("No Passport header found in the request.");
        }

        // 실제 문장 조회 로직
        log.info("Fetching sentence with ID: {}", sentenceId);
        SentenceResponseDto sentence = sentenceService.getSentenceById(sentenceId);
        log.info("Successfully fetched sentence: {}", sentence);

        // 응답 반환
        return ApiResponse.ok(sentence);
    }





//    @GetMapping("/sentence/{sentenceId}")
//    public ApiResponse<SentenceResponseDto> getSentenceById(@PathVariable Long sentenceId) {
//        log.info("Fetching sentence with ID: {}", sentenceId);
//        SentenceResponseDto sentence = sentenceService.getSentenceById(sentenceId);
//        log.info("Successfully fetched sentence: {}", sentence);
//        return ApiResponse.ok(sentence);
//    }

    @PostMapping("/sentence")
    public ApiResponse<SentenceResponseDto> createSentence(@RequestBody SentenceRequestDto requestDto) {
        log.info("Creating a new sentence with request data: {}", requestDto);
        SentenceResponseDto createdSentence = sentenceService.createSentence(requestDto);
        log.info("Successfully created a new sentence with ID: {}", createdSentence.id());
        return ApiResponse.ok(createdSentence);
    }

    @PutMapping("/sentence/{sentenceId}")
    public ApiResponse<SentenceResponseDto> updateSentence(
            @PathVariable Long sentenceId,
            @RequestBody SentenceRequestDto requestDto) {
        log.info("Updating sentence with ID: {} using data: {}", sentenceId, requestDto);
        SentenceResponseDto updatedSentence = sentenceService.updateSentence(sentenceId, requestDto);
        log.info("Successfully updated sentence with ID: {}", updatedSentence.id());
        return ApiResponse.ok(updatedSentence);
    }

    @DeleteMapping("/sentence/{sentenceId}")
    public ApiResponse<String> deleteSentence(@PathVariable Long sentenceId) {
        log.info("Deleting sentence with ID: {}", sentenceId);
        sentenceService.deleteSentence(sentenceId);
        log.info("Successfully deleted sentence with ID: {}", sentenceId);
        return ApiResponse.ok("Sentence deletion successful");
    }

    @GetMapping("/sentences")
    public ApiResponse<PagingResponseDto<SentenceResponseDto>> getSentences(@RequestParam int page) {
        log.info("Fetching sentences for page number: {}", page);
        PagingResponseDto<SentenceResponseDto> pagingResponse = sentenceService.getSentencesPage(page);
        log.info("Successfully fetched {} sentences for page number: {}", pagingResponse.pageSize(), page);
        return ApiResponse.ok(pagingResponse);
    }
}
