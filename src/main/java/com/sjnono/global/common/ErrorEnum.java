package com.sjnono.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter @AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // enum을 Json형식으로 변환
public enum ErrorEnum {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Request Data"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found Data");

    private final HttpStatus httpStatus;
    private final String message;
}
