package com.dunice.Vitushkin_Advanced_REST_Server.dto.user;

import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthDto {
    @Email(message = ErrorsMsg.USER_EMAIL_NOT_VALID, regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = ErrorsMsg.USER_EMAIL_NOT_NULL)
    @Size(min = 3, max = 100, message = ErrorsMsg.EMAIL_SIZE_NOT_VALID)
    private String email;

    @NotBlank(message = ErrorsMsg.PASSWORD_NOT_VALID)
    @Size(min = 5, max = 100, message = ErrorsMsg.PASSWORD_NOT_VALID)
    private String password;
}
