package com.dunice.Vitushkin_Advanced_REST_Server.mapper;

import java.util.List;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.LoginUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.RegisterUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PutUserDtoResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User registerUserDtoToUser(RegisterUserDto registerUserDto);
    LoginUserDto userToLoginUserDto(User user);
    PublicUserView userToPublicUserView(User user);
    PutUserDtoResponse userToPutUserResponse(User user);
    List<PublicUserView> listOfUsersToListOfPublicUserViews(List<User> users);
}
