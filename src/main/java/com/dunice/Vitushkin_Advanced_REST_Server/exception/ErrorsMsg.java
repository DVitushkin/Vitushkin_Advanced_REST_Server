package com.dunice.Vitushkin_Advanced_REST_Server.exception;

public interface ErrorsMsg {
    String USERNAME_SIZE_NOT_VALID = "Username size should be between 3 and 25";
    String ROLE_SIZE_NOT_VALID = "role size not valid";
    String EMAIL_SIZE_NOT_VALID = "email size not valid";
    String MUST_NOT_BE_NULL = "must not be null";
    String USER_NOT_FOUND = "Could not find user";
    String TOKEN_NOT_PROVIDED = "JWT token not provided";
    String UNAUTHORISED = "unauthorised";
    String USER_EMAIL_NOT_NULL = "user email mustn't be null";
    String USER_PASSWORD_NOT_VALID = "user password must be more than 6 symbols";
    String USER_ROLE_NOT_NULL = "user role mustn't be null";
    String USER_EMAIL_NOT_VALID = "user email must be a well-formed email address";
    String EXCEPTION_HANDLER_NOT_PROVIDED = "Exception handler not provided";
    String USER_AVATAR_NOT_NULL = "user avatar mustn't be null";
    String PASSWORD_NOT_VALID = "password not valid";
    String PASSWORD_NOT_NULL = "user password mustn't be null";
    String ID_MUST_BE_POSITIVE = "ID must be grater than zero";
    String USER_ALREADY_EXISTS = "User already exists";
    String USER_WITH_THIS_EMAIL_ALREADY_EXIST = "User with this email already exists";
    String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "Http request not valid";
}
