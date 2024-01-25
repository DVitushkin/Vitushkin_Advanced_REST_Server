package com.dunice.Vitushkin_Advanced_REST_Server.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.PutUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.mapper.UserMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PutUserDtoResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public CustomSuccessResponse<PublicUserView> getUserInfo() {
        User user = this.getUserFromContext();

        PublicUserView userView = userMapper.userToPublicUserView(user);
        return CustomSuccessResponse.withData(userView);
    }


    public CustomSuccessResponse<PutUserDtoResponse> putUserInfo(PutUserDto request) {
        User user = this.getUserFromContext();
        if (userRepository.existsUserByEmail(request.getEmail())
                &&
                !Objects.equals(user.getEmail(), request.getEmail())) {
            throw new EntityExistsException();
        }

        user.setAvatar(request.getAvatar())
                .setEmail(request.getEmail())
                .setName(request.getName())
                .setRole(request.getRole());
        userRepository.save(user);

        PutUserDtoResponse putUserDtoResponse = userMapper.userToPutUserResponse(user);
        return CustomSuccessResponse.withData(putUserDtoResponse);
    }

    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo() {
        List<User> users = userRepository.findAll();
        List<PublicUserView> userViewList = userMapper.listOfUsersToListOfPublicUserViews(users);
        return CustomSuccessResponse.withData(userViewList);
    }

    public CustomSuccessResponse<PublicUserView> getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorsMsg.USER_NOT_FOUND));
        return CustomSuccessResponse.withData(userMapper.userToPublicUserView(user));
    }

    public BaseSuccessResponse deleteUser() {
        User user = this.getUserFromContext();
        userRepository.deleteById(user.getId());
        return BaseSuccessResponse.ok();
    }
}
