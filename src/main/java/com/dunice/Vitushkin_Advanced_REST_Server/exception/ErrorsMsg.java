package com.dunice.Vitushkin_Advanced_REST_Server.exception;

public interface ErrorsMsg {
    String UNKNOWN = "unknown";
    String USERNAME_SIZE_NOT_VALID = "Username size should be between 3 and 25";
    String ROLE_SIZE_NOT_VALID = "role size not valid";
    String EMAIL_SIZE_NOT_VALID = "email size not valid";
    String MUST_NOT_BE_NULL = "must not be null";
    String USER_NOT_FOUND = "Could not find user";
    String TOKEN_NOT_PROVIDED = "JWT token not provided";
    String UNAUTHORIZED = "unauthorised";
    String USER_EMAIL_NOT_NULL = "user email mustn't be null";
    String USER_PASSWORD_NOT_VALID = "user password must be more than 5 symbols";
    String USER_ROLE_NOT_NULL = "user role mustn't be null";
    String NEWS_DESCRIPTION_SIZE_NOT_VALID = "News description size should be between 3 and 130";
    String NEWS_DESCRIPTION_NOT_NULL = "News description mustn't be null";
    String NEWS_TITLE_SIZE = "news title size not valid";
    String NEWS_TITLE_NOT_NULL = "title has to be present";
    String USER_EMAIL_NOT_VALID = "user email must be a well-formed email address";
    String PAGE_SIZE_NOT_VALID = "news page must be greater or equal 1";
    String PER_PAGE_MIN_NOT_VALID = "perPage must be greater or equal 1";
    String PER_PAGE_MAX_NOT_VALID = "perPage must be less or equal 100";
    String EXCEPTION_HANDLER_NOT_PROVIDED = "Exception handler not provided";
    String MAX_UPLOAD_SIZE_EXCEEDED = "Maximum upload size exceeded";
    String USER_AVATAR_NOT_NULL = "user avatar mustn't be null";
    String NEWS_IMAGE_LENGTH = "Image link length should be between 3 and 130";
    String PASSWORD_NOT_VALID = "password not valid";
    String PASSWORD_NOT_NULL = "user password mustn't be null";
    String NEWS_NOT_FOUND = "news not found";
    String ID_MUST_BE_POSITIVE = "ID must be grater than zero";
    String USER_ALREADY_EXISTS = "User already exists";
    String USER_NAME_HAS_TO_BE_PRESENT = "User name has to be present";
    String TAGS_NOT_VALID = "Tags invalid";
    String NEWS_IMAGE_HAS_TO_BE_PRESENT = "Image mustn't be null";
    String USER_WITH_THIS_EMAIL_ALREADY_EXIST = "User with this email already exists";
    String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "Http request not valid";
}
