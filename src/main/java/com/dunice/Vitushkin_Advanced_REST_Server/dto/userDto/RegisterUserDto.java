package com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto;

import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    private String avatar;

    @Email(message = ErrorsMsg.USER_EMAIL_NOT_VALID)
    @NotNull(message = ErrorsMsg.USER_EMAIL_NOT_NULL)
    @Size(min = 3, max = 100, message = ErrorsMsg.EMAIL_SIZE_NOT_VALID)
    private String email;

    @NotNull(message = ErrorsMsg.MUST_NOT_BE_NULL)
    @Size(min = 3, max = 25, message = ErrorsMsg.USERNAME_SIZE_NOT_VALID)
    private String name;

    @NotNull(message = ErrorsMsg.PASSWORD_NOT_NULL)
    @Size(min = 6, max = 25, message = ErrorsMsg.USER_PASSWORD_NOT_VALID)
    private String password;

    @NotNull(message = ErrorsMsg.MUST_NOT_BE_NULL)
    @Size(min = 3, max = 100, message = ErrorsMsg.ROLE_SIZE_NOT_VALID)
    private String role;
}
