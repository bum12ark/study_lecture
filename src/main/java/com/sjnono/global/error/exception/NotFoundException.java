package com.sjnono.global.error.exception;

import com.sjnono.global.error.CustomException;
import com.sjnono.global.error.ErrorEnum;
import org.springframework.hateoas.Link;

public class NotFoundException extends CustomException {
    private final Link link;

    public NotFoundException(Link link) {
        super(ErrorEnum.NOT_FOUND, link);
        this.link = link;
    }
}
