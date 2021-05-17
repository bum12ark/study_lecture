package com.sjnono.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Object data;
    private String slash;
}
