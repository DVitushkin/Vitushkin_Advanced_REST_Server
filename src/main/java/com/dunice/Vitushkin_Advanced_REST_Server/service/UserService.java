package com.dunice.Vitushkin_Advanced_REST_Server.service;

import com.dunice.Vitushkin_Advanced_REST_Server.mapper.UserMapper;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.UserRepository;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

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
}
