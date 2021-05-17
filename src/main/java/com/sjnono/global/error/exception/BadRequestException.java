package com.sjnono.global.error.exception;

import com.sjnono.global.error.CustomException;
import com.sjnono.global.error.ErrorEnum;
import org.springframework.hateoas.Link;

public class BadRequestException extends CustomException {
    private final Link link;

    public BadRequestException(Link link) {
        super(ErrorEnum.INVALID_PARAMETER, link);
        this.link = link;
    }
}
