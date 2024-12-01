package com.example.memoria.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Test Error
    TEST_ERROR(10000, HttpStatus.BAD_REQUEST, "테스트 에러입니다."),

    //400 Bad Request


    // 404 Not Found
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),

    ID_NOT_FOUND_USER(40401, HttpStatus.NOT_FOUND, "해당 유저 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_SENTENCE(40402, HttpStatus.NOT_FOUND, "해당 문장 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_WORD(40403, HttpStatus.NOT_FOUND, "해당 단어 ID를 찾을 수 없습니다."),

    ID_NOT_FOUND_REFERENCE(40405, HttpStatus.NOT_FOUND, "해당 참조 ID를 찾을 수 없습니다."),
    NOT_FOUND_PASSPORT(40406, HttpStatus.NOT_FOUND, "Passport가 없습니다."),


    // 409 Conflict
    REFERENCE_ALREADY_EXISTS(40901, HttpStatus.CONFLICT, "참조는 문장당 1개만 존재"),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
