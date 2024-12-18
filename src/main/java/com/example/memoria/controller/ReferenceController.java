package com.example.memoria.controller;

import com.example.memoria.dto.ReferenceRequestDto;
import com.example.memoria.dto.ReferenceResponseDto;
import com.example.memoria.service.ReferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.example.api.response.ApiResponse;


@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reference")
public class ReferenceController {

    private final ReferenceService referenceService;

    @GetMapping("/{referenceId}")
    public ApiResponse<ReferenceResponseDto> getReferenceById(@PathVariable Long referenceId) {
        ReferenceResponseDto reference = referenceService.getReferenceById(referenceId);
        return ApiResponse.ok(reference);
    }

    @PostMapping()
    public ApiResponse<ReferenceResponseDto> createReference(@RequestBody ReferenceRequestDto requestDto) {
        ReferenceResponseDto createdReference = referenceService.createReference(requestDto);
        return ApiResponse.created(createdReference);
    }

    // ReferenceController.java
    @PutMapping("/{referenceId}")
    public ApiResponse<ReferenceResponseDto> updateReference(@PathVariable Long referenceId, @RequestBody ReferenceRequestDto requestDto) {
        ReferenceResponseDto updatedReference = referenceService.updateReference(referenceId, requestDto);
        return ApiResponse.ok(updatedReference);
    }

    @DeleteMapping("/{referenceId}")
    public ApiResponse<String> deleteReference(@PathVariable Long referenceId) {
        referenceService.deleteReference(referenceId);
        return ApiResponse.ok("참조 삭제 성공");
    }

    // sentenceId로 Reference 조회
    @GetMapping("/sentence/{sentenceId}")
    public ApiResponse<ReferenceResponseDto> getReferenceBySentenceId(@PathVariable Long sentenceId) {
        ReferenceResponseDto reference = referenceService.getReferenceBySentenceId(sentenceId);
        return ApiResponse.ok(reference);
    }
}

