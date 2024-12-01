package com.example.memoria.util;

import com.example.api.response.CustomException;
import com.example.memoria.response.ErrorCode;
import com.example.memoria.dto.Passport;

import java.util.Optional;

public class PassportUtil {

    // Passport 객체에서 userId를 추출하는 메서드
    public static Long getUserId() {
        return Optional.ofNullable(RequestContextUtil.getPassport())
                .map(Passport::userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PASSPORT));
    }
}
