package com.example.memoria.controller;

import com.example.api.response.ApiResponse;
import com.example.memoria.response.ErrorCode;
import com.example.memoria.service.TextToSpeechService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/tts")
public class TextToSpeechController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    
    // 문장을 음성 파일로 로컬에 저장
    @PostMapping("/test")
    public ApiResponse<String> generateSpeechTest(@RequestParam String text) {
        try {
            // 출력 파일 이름 지정
            String filePath = textToSpeechService.synthesizeSpeechToFile(text);
            log.info("audio 파일 생성");

            return ApiResponse.ok("Audio file generated: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.fail(ErrorCode.TTS_GENERATION_FAILED);
        }
    }

//    @PostMapping("/generate")
//    public ApiResponse<String> generateSpeech(@RequestParam String text, HttpServletResponse response) {
//        try {
//            // 텍스트를 음성 데이터(byte[])로 변환
//            byte[] audioData = textToSpeechService.synthesizeSpeech(text);
//            log.info("Audio data generated");
//
//            // 응답 헤더 설정 (MP3 파일로 전송)
//            response.setContentType("audio/mpeg");
//            response.setContentLength(audioData.length);
//            response.setHeader("Content-Disposition", "attachment; filename=\"audio.mp3\"");
//
//            // MP3 음성 데이터를 응답으로 반환 (헤더를 직접 설정하여 반환)
//            response.getOutputStream().write(audioData); // 음성 데이터를 직접 클라이언트로 전송
//            response.getOutputStream().flush(); // 데이터 전송 완료
//
//            return ApiResponse.ok("Already send");  // 실제 응답을 ApiResponse로 래핑하여 반환
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ApiResponse.fail(ErrorCode.TTS_GENERATION_FAILED);  // 실패 응답
//        }
//    }


    // 문장을 음성 파일 바이트 코드로 반환
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateSpeech(@RequestParam String text) {
        try {
            // 텍스트를 음성 데이터(byte[])로 변환
            byte[] audioData = textToSpeechService.synthesizeSpeech(text);
            log.info("Audio data generated");

            // 응답 헤더 설정 (MP3 파일로 전송)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));
            headers.setContentLength(audioData.length);
            headers.setContentDispositionFormData("attachment", "audio.mp3");

            // 음성 데이터를 ResponseEntity로 반환
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(audioData);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // 실패 시 음성 데이터는 없으므로 null 반환
        }
    }
}