package com.dunice.Vitushkin_Advanced_REST_Server.service.auth;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.AuthDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.jwt.JwtTokenUtil;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.UserMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomSuccessResponse<LoginUserDto> register(RegisterUserDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new EntityExistsException();
        }

        User user = userMapper.registerUserDtoToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        LoginUserDto loginUserDto = userMapper.userToLoginUserDto(user);
        loginUserDto.setToken(jwtTokenUtil.generateToken(user));
        return CustomSuccessResponse.withData(loginUserDto);
    }

    public CustomSuccessResponse<LoginUserDto> login(AuthDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException(ErrorsMsg.USER_NOT_FOUND));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        request.getPassword()
                )
        );

        LoginUserDto loginUserDto = userMapper.userToLoginUserDto(user);
        loginUserDto.setToken(jwtTokenUtil.generateToken(user));
        return CustomSuccessResponse.withData(loginUserDto);
    }
}
