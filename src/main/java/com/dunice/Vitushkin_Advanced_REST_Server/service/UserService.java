package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.mapper.UserMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CustomSuccessResponse<PublicUserView> getUserInfo(Principal connectedUser) {
        String email = connectedUser.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        PublicUserView userView = userMapper.userToPublicUserView(user);
        return CustomSuccessResponse.withData(userView);
    }

    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo() {
        List<User> users = userRepository.findAll();
        List<PublicUserView> userViewList = userMapper.listOfUsersToListOfPublicUserViews(users);
        return CustomSuccessResponse.withData(userViewList);
    }

    public CustomSuccessResponse<PublicUserView> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return CustomSuccessResponse.withData(userMapper.userToPublicUserView(user));
    }
}
