package com.sjnono.global.error;

import com.sjnono.global.error.ErrorEnum;
import lombok.Getter;
import org.springframework.hateoas.Link;

@Getter
public class CustomException extends RuntimeException {
    private ErrorEnum errorEnum;
    private Link link;

    public CustomException(ErrorEnum errorEnum, Link link) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
        this.link = link;
    }
}
