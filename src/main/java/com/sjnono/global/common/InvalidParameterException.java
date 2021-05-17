package com.sjnono.global.common;

import org.springframework.hateoas.Link;

public class InvalidParameterException extends CustomException {
    private final Link link;

    public InvalidParameterException(Link link) {
        super(ErrorEnum.INVALID_PARAMETER, link);
        this.link = link;
    }
}
