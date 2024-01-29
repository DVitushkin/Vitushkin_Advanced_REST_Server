package com.dunice.Vitushkin_Advanced_REST_Server.exception;

import java.util.List;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.storage.fileStorage.FileStorageException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {

    private static Integer[] parseValidateErrors(List<? extends MessageSourceResolvable> ex) {
        return ex.stream()
                .map(objectError -> ErrorsCode.getErrCodeByErrMsg(objectError.getDefaultMessage()))
                .toArray(Integer[]::new);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Integer[] codes = parseValidateErrors(ex
                .getBindingResult()
                .getAllErrors()
        );
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
                .body(CustomSuccessResponse.withCode(ErrorsCode.getErrCodeByErrMsg(ex.getMessage())));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleEntityExistsExceptions(EntityExistsException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.USER_ALREADY_EXISTS.getStatusCode()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleNoResourceFoundExceptions(NoResourceFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.MAX_UPLOAD_SIZE_EXCEEDED.getStatusCode()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleHandlerMethodValidationExceptions(HandlerMethodValidationException ex) {
        Integer[] codes = parseValidateErrors(ex.getAllErrors());
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(codes));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleJwtExceptions(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(CustomSuccessResponse.withCode(ErrorsCode.UNAUTHORIZED.getStatusCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleBadCredentialsExceptions(BadCredentialsException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.PASSWORD_NOT_VALID.getStatusCode()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleFileStorageExceptions(FileStorageException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.EXCEPTION_HANDLER_NOT_PROVIDED.getStatusCode()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<CustomSuccessResponse<?>> handleFileUploadExceptions(FileUploadException ex) {
        return ResponseEntity
                .badRequest()
                .body(CustomSuccessResponse.withCode(ErrorsCode.UNKNOWN.getStatusCode()));
    }
}
