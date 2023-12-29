package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User RegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return User.builder()
                .avatar(registerUserDto.getAvatar())
                .email(registerUserDto.getEmail())
                .name(registerUserDto.getName())
                .role(registerUserDto.getRole())
                .build();
    }

    @Override
    public LoginUserDto UserToLoginUserDto(User user) {
        return LoginUserDto
                .builder()
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
