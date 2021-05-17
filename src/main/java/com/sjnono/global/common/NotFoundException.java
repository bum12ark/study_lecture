package com.sjnono.global.common;

import org.springframework.hateoas.Link;

public class NotFoundException extends CustomException {
    private final Link link;

    public NotFoundException(Link link) {
        super(ErrorEnum.NOT_FOUND, link);
        this.link = link;
    }
}
