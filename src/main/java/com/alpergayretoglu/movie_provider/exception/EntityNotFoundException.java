package com.alpergayretoglu.movie_provider.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends BusinessException {

    private final HttpStatus statusCode = HttpStatus.NOT_FOUND;
    private final String message;

    public EntityNotFoundException(String message) {
        super(ErrorCode.RESOURCE_MISSING, message);
        this.message = message;
    }

    public EntityNotFoundException() {
        this("Entity with given credentials was not found");
    }

}
