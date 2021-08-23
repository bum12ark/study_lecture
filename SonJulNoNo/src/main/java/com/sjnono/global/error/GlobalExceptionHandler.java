package com.sjnono.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customException(CustomException e) {
        logger.info("###################################################");
        logger.info("## ErrorEnum: "+e.getErrorEnum());
        logger.info("## Link: "+e.getLink());
        logger.info("###################################################");

        EntityModel<ErrorEnum> model = EntityModel.of(e.getErrorEnum())
                .add(e.getLink());
        return ResponseEntity
                .status(e.getErrorEnum().getHttpStatus())
                .body(model);
    }
}
