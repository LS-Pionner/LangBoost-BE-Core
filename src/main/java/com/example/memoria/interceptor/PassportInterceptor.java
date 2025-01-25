package com.example.memoria.interceptor;

import com.example.memoria.dto.Passport;
import com.example.memoria.util.RequestContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
@Component
public class PassportInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;
    private final String currentEnvironment;  // 개발용 실행인지 확인

    // 생성자 주입 방식으로 @Value 사용
    public PassportInterceptor(ObjectMapper objectMapper, @Value("${spring.profiles.active}") String activeProfile) {
        this.objectMapper = objectMapper;
        this.currentEnvironment = activeProfile;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // x-passport 헤더에서 Passport 객체 추출
        String passportHeader = request.getHeader("x-passport");

        log.info("PassportInterceptor - preHandle: Received x-passport header = {}", passportHeader);

        // 개발 테스트 시 passport 없이도 가능
//        if ("dev".equals(currentEnvironment) && passportHeader == null) {
        // 임시 설정
        if (passportHeader == null) {

            log.warn("PassportInterceptor - preHandle: Dev mode active, injecting default Passport");
            // Passport 임의로 설정
            Passport defaultPassport = new Passport(1L, "example@example@com");
            RequestContextUtil.setPassport(defaultPassport);
            return true;
        }

        if (passportHeader != null) {
            try {
                // Passport 객체로 역직렬화
                Passport passport = objectMapper.readValue(passportHeader, Passport.class);
                log.debug("PassportInterceptor - preHandle: Deserialized Passport = {}", passport);

                // Passport 객체를 RequestContext에 저장 (ThreadLocal)
                RequestContextUtil.setPassport(passport);
            } catch (Exception e) {
                log.error("PassportInterceptor - preHandle: Failed to deserialize Passport from header", e);
                throw e; // 예외를 던져 요청 처리를 중단
            }
        } else {
            log.warn("PassportInterceptor - preHandle: x-passport header is missing");
        }

        return true;  // 요청을 계속 처리하도록 허용
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("PassportInterceptor - afterCompletion: Clearing RequestContext");

        // 요청 후 ThreadLocal 정리
        RequestContextUtil.clear();

        if (ex != null) {
            log.error("PassportInterceptor - afterCompletion: Exception occurred during request processing", ex);
        }
    }
}
