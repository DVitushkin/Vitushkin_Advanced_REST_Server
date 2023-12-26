package com.dunice.Vitushkin_Advanced_REST_Server.exception;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

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
        return CustomSuccessResponse.badRequest(codes);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleHttpMessageNotReadableExceptions(
            HttpMessageNotReadableException ex) {
        return CustomSuccessResponse.badRequest(ErrorsCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getStatusCode());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        return CustomSuccessResponse.badRequest(ErrorsCode.USER_NOT_FOUND.getStatusCode());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleEntityExistsExceptions(EntityExistsException ex) {
        return CustomSuccessResponse.badRequest(ErrorsCode.USER_WITH_THIS_EMAIL_ALREADY_EXIST.getStatusCode());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleHandlerMethodValidationExceptions(HandlerMethodValidationException ex) {
        return CustomSuccessResponse.badRequest(ErrorsCode.ID_MUST_BE_POSITIVE.getStatusCode());
    }
}
