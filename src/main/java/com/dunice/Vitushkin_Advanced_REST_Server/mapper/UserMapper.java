package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.userDto.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User RegisterUserDtoToUser(RegisterUserDto registerUserDto);
    LoginUserDto UserToLoginUserDto(User user, String token);
}
