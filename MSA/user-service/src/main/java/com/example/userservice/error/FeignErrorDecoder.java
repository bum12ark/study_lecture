package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j @Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.debug("method key = {}", methodKey);

        switch (response.status()) {
            case 404:
                if (methodKey.contains("getOrders")) {
                    return new ResponseStatusException(
                            HttpStatus.valueOf(response.status()), "User's order is empty"
                    );
                }
                break;
            default:
                return new Exception("default Exception");
        }

        return null;
    }

}
