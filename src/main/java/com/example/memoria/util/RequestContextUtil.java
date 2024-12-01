package com.example.memoria.util;


import com.example.memoria.dto.Passport;

public class RequestContextUtil {

    // Passport 객체를 저장하기 위한 ThreadLocal 변수
    private static final ThreadLocal<Passport> passportThreadLocal = new ThreadLocal<>();

    // Passport 객체 저장
    public static void setPassport(Passport passport) {
        passportThreadLocal.set(passport);
    }

    // Passport 객체 가져오기
    public static Passport getPassport() {
        return passportThreadLocal.get();
    }

    // ThreadLocal 데이터 삭제
    public static void clear() {
        passportThreadLocal.remove();
    }
}
