package com.alpergayretoglu.movie_provider.controller;

import com.alpergayretoglu.movie_provider.exception.BusinessException;
import com.alpergayretoglu.movie_provider.exception.EntityNotFoundException;
import com.alpergayretoglu.movie_provider.exception.ErrorCode;
import com.alpergayretoglu.movie_provider.exception.ErrorDTO;
import com.alpergayretoglu.movie_provider.util.DateUtil;
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
        LOGGER.info("Business Error: {}", ex.getMessage());
        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ex.getErrorCode().getHttpCode())
                .error(ex.getErrorCode().toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(error.getStatus()));
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        LOGGER.info("handleMethodArgumentNotValid: {}", ex.getMessage());
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
        LOGGER.info("handleMethodArgumentNotValid: {}", ex.getMessage());
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorDTO error = ErrorDTO.builder()
                .timestamp(DateUtil.now())
                .status(ErrorCode.VALIDATION.getHttpCode())
                .error(ErrorCode.VALIDATION.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(error.getStatus()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        LOGGER.info("handleMissingServletRequestPart: {}", ex.getMessage());
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
        LOGGER.info("handleException: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    /* TODO: These do not work properly, fix them or remove them
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
     */
}
