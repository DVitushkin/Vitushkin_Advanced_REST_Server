package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.jwt.JwtTokenUtil;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private LoginUserDto UserToLoginUserDtoWithToken(User user, String token) {
        return  LoginUserDto
                .builder()
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .token(token)
                .build();
    }

    public CustomSuccessResponse<LoginUserDto> register(RegisterUserDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new EntityExistsException();
        }
        User user = User.builder()
                .avatar(request.getAvatar())
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return CustomSuccessResponse.data(
                UserToLoginUserDtoWithToken(
                        userRepository.save(user),
                        jwtTokenUtil.generateToken(user)
                        )
                );
    }
}
