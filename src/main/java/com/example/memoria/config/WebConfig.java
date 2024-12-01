package com.example.memoria.config;

import com.example.memoria.interceptor.PassportInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PassportInterceptor passportInterceptor;

    public WebConfig(PassportInterceptor passportInterceptor) {
        this.passportInterceptor = passportInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor)
                .addPathPatterns("/api/**");  // 인터셉터를 적용할 URL 패턴 지정
    }
}
