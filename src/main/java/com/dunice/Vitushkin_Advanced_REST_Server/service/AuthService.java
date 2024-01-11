package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.jwt.JwtTokenUtil;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.UserMapper;
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

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public CustomSuccessResponse<LoginUserDto> register(RegisterUserDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new EntityExistsException();
        }

        User user = userMapper.registerUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        LoginUserDto loginUserDto = userMapper.userToLoginUserDto(user);
        loginUserDto.setToken(jwtTokenUtil.generateToken(user));
        return CustomSuccessResponse.data(loginUserDto);
    }
}
