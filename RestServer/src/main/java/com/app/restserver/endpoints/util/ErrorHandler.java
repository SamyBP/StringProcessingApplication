package com.app.restserver.endpoints.util;

import com.app.restserver.endpoints.responses.ErrorResponse;
import com.app.restserver.services.NotAuthorizedException;
import com.app.restserver.validations.authentication.InvalidPasswordException;
import com.app.restserver.validations.module.InvalidModuleException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(400)
                .message(fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(400)
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handle(PSQLException e, HttpServletRequest request) {
        ServerErrorMessage serverErrorMessage = e.getServerErrorMessage();
        assert serverErrorMessage != null;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(400)
                .message(serverErrorMessage.getDetail())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidPasswordException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(400)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public void handle() {
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handle(NotAuthorizedException e, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(401)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidModuleException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidModuleException e, HttpServletRequest request) {
        log(e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .path(request.getRequestURI())
                .status(400)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private void log(Throwable var) {
        logger.warn(String.format("Resolved [%s]", var.getMessage()));
    }

}
