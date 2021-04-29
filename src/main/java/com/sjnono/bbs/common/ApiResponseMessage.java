package com.sjnono.bbs.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder @Data @AllArgsConstructor
public class ApiResponseMessage {
    private StatusEnum status;
    private String message;
    private Object data;

    public ApiResponseMessage() {
        this.status = StatusEnum.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }

}
