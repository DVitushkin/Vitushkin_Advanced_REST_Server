package com.dunice.Vitushkin_Advanced_REST_Server.exception;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class ExceptionApiHandler {

    private static Integer[] parseValidateErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> ErrorsCode.getErrCodeByErrMsg(objectError.getDefaultMessage()))
                .toArray(Integer[]::new);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Integer[] codes = parseValidateErrors(ex);
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(codes));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleHttpMessageNotReadableExceptions(
            HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getStatusCode()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.USER_NOT_FOUND.getStatusCode()));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleEntityExistsExceptions(EntityExistsException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.USER_ALREADY_EXISTS.getStatusCode()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleHandlerMethodValidationExceptions(HandlerMethodValidationException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.ID_MUST_BE_POSITIVE.getStatusCode()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleJwtExceptions(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CustomSuccessResponse.withCode(ErrorsCode.UNAUTHORIZED.getStatusCode()));
    }
}
