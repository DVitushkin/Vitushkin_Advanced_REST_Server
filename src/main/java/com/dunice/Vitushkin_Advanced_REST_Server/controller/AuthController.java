package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CustomSuccessResponse<LoginUserDto>> registerUser(
            @Validated @RequestBody RegisterUserDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }
}