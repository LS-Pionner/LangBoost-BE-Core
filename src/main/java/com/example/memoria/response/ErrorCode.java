package com.example.memoria.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.example.api.response.ErrorCodeInterface;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeInterface {

    //400 Bad Request

    // 404 Not Found

    ID_NOT_FOUND_USER(40401, HttpStatus.NOT_FOUND, "해당 유저 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_SENTENCE(40402, HttpStatus.NOT_FOUND, "해당 문장 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_WORD(40403, HttpStatus.NOT_FOUND, "해당 단어 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_REFERENCE(40405, HttpStatus.NOT_FOUND, "해당 참조 ID를 찾을 수 없습니다."),

    NOT_FOUND_PASSPORT(40406, HttpStatus.NOT_FOUND, "Passport가 없습니다."),


    // 409 Conflict
    REFERENCE_ALREADY_EXISTS(40901, HttpStatus.CONFLICT, "참조는 문장당 1개만 존재"),

    // 500 Internal Server Error
    TTS_GENERATION_FAILED(50001, HttpStatus.INTERNAL_SERVER_ERROR, "TTS 생성 중 오류가 발생했습니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
