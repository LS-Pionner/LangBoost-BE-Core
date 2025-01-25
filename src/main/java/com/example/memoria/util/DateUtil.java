package com.example.memoria.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    // 마지막 조회 날짜 반환
    public static LocalDate getLastViewedDate() {
        return LocalDate.now();
    }
}
