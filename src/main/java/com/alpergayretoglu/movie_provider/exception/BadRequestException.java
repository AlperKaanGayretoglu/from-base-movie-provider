package com.alpergayretoglu.movie_provider.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BusinessException {

    private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;
    private final String message;

    public BadRequestException(String message) {
        super(ErrorCode.UNKNOWN, message);
        this.message = message;
    }

    public BadRequestException() {
        this("The request has been rejected");
    }

}