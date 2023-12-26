package com.dunice.Vitushkin_Advanced_REST_Server.exception;

import lombok.Getter;

@Getter
public enum ErrorsCode {
    USERNAME_SIZE_NOT_VALID(1, "Username size should be between 3 and 25"),
    ROLE_SIZE_NOT_VALID(2, "role size not valid"),
    EMAIL_SIZE_NOT_VALID(3, "email size not valid"),
    MUST_NOT_BE_NULL(4, "must not be null"),
    USER_NOT_FOUND(5, "Could not find user"),
    TOKEN_NOT_PROVIDED(6, "JWT token not provided"),
    UNAUTHORISED(7, "unauthorised"),
    USER_EMAIL_NOT_NULL(8, "user email mustn't be null"),
    USER_PASSWORD_NOT_VALID(9, "user password must be more than 6 symbols"),
    USER_ROLE_NOT_NULL(10, "user role mustn't be null"),
    USER_EMAIL_NOT_VALID(17, "user email must be a well-formed email address"),
    EXCEPTION_HANDLER_NOT_PROVIDED(21, "Exception handler not provided"),
    USER_AVATAR_NOT_NULL(24, "user avatar mustn't be null"),
    PASSWORD_NOT_VALID(25, "password not valid"),
    PASSWORD_NOT_NULL(26, "user password mustn't be null"),
    ID_MUST_BE_POSITIVE(29, "ID must be grater than zero"),
    USER_ALREADY_EXISTS(30, "User already exists"),
    USER_WITH_THIS_EMAIL_ALREADY_EXIST(46, "User with this email already exists"),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(47, "Http request not valid");

    private final Integer statusCode;
    private final String msg;

    ErrorsCode(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public static Integer getErrCodeByErrMsg(String msg) {
        for (ErrorsCode e : ErrorsCode.values()) {
            if (e.msg.equals(msg)) {
                return e.getStatusCode();
            }
        }
        return 0;
    }
}
