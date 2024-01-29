package com.dunice.Vitushkin_Advanced_REST_Server.service.auth;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.AuthDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;

public interface AuthService {
    public CustomSuccessResponse<LoginUserDto> register(RegisterUserDto request);

    public CustomSuccessResponse<LoginUserDto> login(AuthDto request);


}
