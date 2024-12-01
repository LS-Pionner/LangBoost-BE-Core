package com.example.memoria.response;

import com.example.memoria.dto.ExceptionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        @JsonIgnore
        HttpStatus httpStatus,
        boolean success,
        @Nullable T payload,
        @Nullable ExceptionDto error
) {
        public static <T> ApiResponse<T> ok(@Nullable final T data) {
                return new ApiResponse<>(HttpStatus.OK, true, data, null);
        }

        public static <T> ApiResponse<T> created(@Nullable final T data) {
                return new ApiResponse<>(HttpStatus.CREATED, true, data, null);
        }

        public static <T> ApiResponse<T> fail(final ErrorCode c) {
                return new ApiResponse<>(c.getHttpStatus(), false, null, ExceptionDto.of(c));
        }



}

