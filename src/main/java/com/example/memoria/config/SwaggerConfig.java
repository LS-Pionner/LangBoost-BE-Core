package com.example.memoria.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addHeaders("x-passport", new Header()
                                .description("Serialized Passport Object")
                                .schema(new StringSchema()))) // x-passport 헤더 추가
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("LB Core Server API Test") // API의 제목
                .description("LangBoost Core Server API Test") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}

