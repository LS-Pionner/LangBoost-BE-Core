package com.example.memoria.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.example.memoria.dto.Passport;

import static org.junit.jupiter.api.Assertions.*;

class RequestContextUtilTest {

    @AfterEach
    void tearDown() {
        // Ensure ThreadLocal is cleared after each test
        RequestContextUtil.clear();
    }

    // context에 passport 초기화 및 가져오는 함수 테스트
    @Test
    void testSetAndGetPassport() {
        // Arrange : passport 준비
        Passport passport = new Passport(123L, "user@example.com");

        // Act : 초기화 및 가져오기
        RequestContextUtil.setPassport(passport);
        Passport retrievedPassport = RequestContextUtil.getPassport();

        // Assert : 결과값
        assertNotNull(retrievedPassport, "Passport should not be null");
        assertEquals(123L, retrievedPassport.userId(), "User ID should match the stored Passport");
        assertEquals("user@example.com", retrievedPassport.email(), "Email should match the stored Passport");
    }

    // context에 passport 지우기
    @Test
    void testClearPassport() {
        // Arrange : passport context 설정
        Passport passport = new Passport(456L, "clear@example.com");
        RequestContextUtil.setPassport(passport);

        // Act : context 지우기
        RequestContextUtil.clear();
        Passport retrievedPassport = RequestContextUtil.getPassport();

        // Assert
        assertNull(retrievedPassport, "Passport should be null after clear");
    }

    @Test
    void testThreadLocalIsolation() throws InterruptedException {
        // Arrange
        Passport mainThreadPassport = new Passport(789L, "main@example.com");
        RequestContextUtil.setPassport(mainThreadPassport);

        // Act
        Thread newThread = new Thread(() -> {
            Passport newThreadPassport = new Passport(999L, "thread@example.com");
            RequestContextUtil.setPassport(newThreadPassport);

            // Assert in new thread
            Passport retrievedPassport = RequestContextUtil.getPassport();
            assertNotNull(retrievedPassport, "Passport in new thread should not be null");
            assertEquals(999L, retrievedPassport.userId(), "User ID in new thread should match");
            assertEquals("thread@example.com", retrievedPassport.email(), "Email in new thread should match");
        });
        newThread.start();
        newThread.join();

        // Assert in main thread
        Passport retrievedPassport = RequestContextUtil.getPassport();
        assertNotNull(retrievedPassport, "Passport in main thread should not be null");
        assertEquals(789L, retrievedPassport.userId(), "User ID in main thread should match");
        assertEquals("main@example.com", retrievedPassport.email(), "Email in main thread should match");
    }
}
