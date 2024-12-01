package com.example.memoria.dto;

import com.example.memoria.response.ErrorCode;
import jakarta.validation.constraints.NotNull;

public record ExceptionDto (
        @NotNull Integer code,
        @NotNull String message
) {

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}
