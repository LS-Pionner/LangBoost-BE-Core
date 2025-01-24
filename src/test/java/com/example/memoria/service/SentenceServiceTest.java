package com.example.memoria.service;

import com.example.memoria.dto.SentenceRequestDto;
import com.example.memoria.dto.SentenceResponseDto;
import com.example.memoria.entity.Sentence;
import com.example.memoria.repository.SentenceRepository;
import com.example.memoria.util.DateUtil;
import com.example.memoria.util.PassportUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SentenceServiceTest {

    @Mock
    private SentenceRepository sentenceRepository;
    @InjectMocks
    private SentenceService sentenceService;  // 실제 테스트할 서비스 클래스

    private MockedStatic<PassportUtil> passportUtilMock;
    private MockedStatic<DateUtil> dateUtilMock;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);

        // Static 메서드 모킹 시작
        passportUtilMock = mockStatic(PassportUtil.class);
        dateUtilMock = mockStatic(DateUtil.class);
    }

    @Test
    void testCreateSentence() {
        // Arrange
        Long userId = 1L; // 예시로 사용될 사용자 ID
        String sentence = "Test Sentence";
        String description = "Test Description";
        LocalDate date = LocalDate.now(); // 현재 날짜를 사용
        SentenceRequestDto requestDto = new SentenceRequestDto(sentence, description);

        Sentence expectedSentence = new Sentence(1L, userId, sentence, description, null, null, null); // 예상되는 저장된 Sentence 객체
        SentenceResponseDto expectedResponse = new SentenceResponseDto(expectedSentence);

        // 모킹: PassportUtil.getUserId()가 호출되면 1L을 반환
        passportUtilMock.when(PassportUtil::getUserId).thenReturn(userId);

        // 모킹: DateUtil.getLastViewedDate()가 호출되면 현재 날짜를 반환
        dateUtilMock.when(DateUtil::getLastViewedDate).thenReturn(date);

        // 모킹: sentenceRepository.save()가 호출되면 expectedSentence 반환
        when(sentenceRepository.save(any(Sentence.class))).thenReturn(expectedSentence);

        // Act
        SentenceResponseDto responseDto = sentenceService.createSentence(requestDto);

        // Assert
        assertEquals(expectedResponse, responseDto);

        // 모킹된 메서드들이 호출되었는지 확인
        verify(sentenceRepository, times(1)).save(any(Sentence.class));

        // Static 메서드들이 호출되었는지 확인
        passportUtilMock.verify(PassportUtil::getUserId, times(1));
        dateUtilMock.verify(DateUtil::getLastViewedDate, times(1));
    }


    @AfterEach
    void tearDown() {
        // Static 메서드 모킹 종료
        passportUtilMock.close();
        dateUtilMock.close();
    }
}
