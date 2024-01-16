package com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {
    private String avatar;
    private String email;
    private Long id;
    private String name;
    private String role;
    private String token;
}
