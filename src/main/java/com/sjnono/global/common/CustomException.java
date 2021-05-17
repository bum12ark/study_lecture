package com.sjnono.global.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorResponse errorResponse;

    public CustomException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
