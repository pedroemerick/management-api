package com.ek.management.exception;

import com.ek.management.controller.v1.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ InvalidIdentifierException.class })
    public ResponseEntity<?> handleInvalidIdentifier(InvalidIdentifierException ex,
                                                          WebRequest request) {
        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(OffsetDateTime.now())
                .title("Business rule violation")
                .messages(List.of(ex.getMessage()))
                .build();

        return this.handleExceptionInternal(ex, errorDTO, new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        var messages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(OffsetDateTime.now())
                .title("One or more invalid fields")
                .messages(messages)
                .build();

        return this.handleExceptionInternal(ex, errorDTO, headers, status, request);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleGlobalException(Exception ex,
                                                     WebRequest request) {
        LOGGER.error("Unexpected error", ex);

        var errorDTO = ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(OffsetDateTime.now())
                .title("Unexpected error")
                .messages(List.of("Unexpected error, contact application admin."))
                .build();

        return this.handleExceptionInternal(ex, errorDTO, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {

        if (body instanceof ProblemDetail) {
            body = ErrorDTO.builder()
                    .status(statusCode.value())
                    .timestamp(OffsetDateTime.now())
                    .title(((ProblemDetail) body).getTitle())
                    .messages(List.of(((ProblemDetail) body).getDetail()))
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
