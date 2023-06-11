package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.exception.*;
import com.alpergayretoglu.movie_provider.util.DateUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> customHandleBusinessException(BusinessException ex, WebRequest request) {
        LOGGER.info("Business Error:");
        ex.printStackTrace();

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ex.getErrorCode().getHttpCode())
                .error(ex.getErrorCode().toString())
                .message(ex.getMessage())
                .build();

        HttpStatus status = HttpStatus.resolve(error.getStatus());
        return new ResponseEntity<>(error, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // error handle for @Valid
    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        LOGGER.info("handleMethodArgumentNotValid:");
        ex.printStackTrace();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ErrorCode.VALIDATION.getHttpCode())
                .error(ErrorCode.VALIDATION.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(ConstraintViolationException ex, WebRequest request) {
        LOGGER.info("handleMethodArgumentNotValid:");
        ex.printStackTrace();

        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ErrorCode.VALIDATION.getHttpCode())
                .error(ErrorCode.VALIDATION.name())
                .message(String.join(", ", errors))
                .build();

        HttpStatus status = HttpStatus.resolve(error.getStatus());
        return new ResponseEntity<>(error, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            @NotNull HttpHeaders headers,
            HttpStatus status,
            @NotNull WebRequest request
    ) {
        LOGGER.info("handleMissingServletRequestPart:");
        ex.printStackTrace();

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(status.value())
                .error(ErrorCode.UNKNOWN.name())
                .message(ex.getRequestPartName() + " is missing!")
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e) {
        LOGGER.info("EntityNotFoundException:");
        e.printStackTrace();

        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleException(BadRequestException e) {
        LOGGER.info("BadRequestException:");
        e.printStackTrace();

        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        LOGGER.info("Exception:");
        e.printStackTrace();

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
