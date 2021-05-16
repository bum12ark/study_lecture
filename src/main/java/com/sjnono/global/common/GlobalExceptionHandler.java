package com.sjnono.global.common;

import com.sjnono.domain.user.UserInfoRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // TODO 공통 Error Handler에 대한 구현 필요
    @ExceptionHandler(CustomException.class)
    public ResponseEntity customException(CustomException e) {
        logger.debug(this.getClass().toString(), "customException");
        EntityModel<ErrorResponse> model = EntityModel.of(e.getErrorResponse())
                .add(linkTo(this.getClass()).slash(e.getErrorResponse().getSlash()).withSelfRel());
        return ResponseEntity.badRequest().body(model);
    }
}
