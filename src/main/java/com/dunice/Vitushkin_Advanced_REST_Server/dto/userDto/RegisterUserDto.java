package com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto;

import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    @NotBlank(message = ErrorsMsg.USER_AVATAR_NOT_NULL)
    private String avatar;

    @Email(message = ErrorsMsg.USER_EMAIL_NOT_VALID, regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank(message = ErrorsMsg.USER_EMAIL_NOT_NULL)
    @Size(min = 3, max = 100, message = ErrorsMsg.EMAIL_SIZE_NOT_VALID)
    private String email;

    @NotBlank(message = ErrorsMsg.USER_NAME_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 25, message = ErrorsMsg.USERNAME_SIZE_NOT_VALID)
    private String name;

    @NotBlank(message = ErrorsMsg.PASSWORD_NOT_NULL)
    @Size(min = 5, max = 25, message = ErrorsMsg.USER_PASSWORD_NOT_VALID)
    private String password;

    @NotBlank(message = ErrorsMsg.USER_ROLE_NOT_NULL)
    @Size(min = 3, max = 100, message = ErrorsMsg.UNKNOWN)
    private String role;
}
