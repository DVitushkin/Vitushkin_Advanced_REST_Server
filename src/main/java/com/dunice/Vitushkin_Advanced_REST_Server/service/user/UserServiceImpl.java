package com.dunice.Vitushkin_Advanced_REST_Server.service.user;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private User getUserFromPrincipal(Principal principal) {
        Optional<User> user = userRepository.findById(UUID.fromString(principal.getName()));
        if (user.isEmpty()) {
            throw new EntityNotFoundException(ErrorsMsg.USER_NOT_FOUND);
        }
        return user.get();

    }

    @Override
    public CustomSuccessResponse<PublicUserView> getUserInfo(Principal principal) {
        User user = this.getUserFromPrincipal(principal);
        PublicUserView userView =  UserMapper.INSTANCE.userToPublicUserView(user);
        return CustomSuccessResponse.withData(userView);
    }

    @Override
    public CustomSuccessResponse<PutUserDtoResponse> putUserInfo(Principal principal, PutUserDto request) {
        User user = this.getUserFromPrincipal(principal);
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

        PutUserDtoResponse putUserDtoResponse =  UserMapper.INSTANCE.userToPutUserResponse(user);
        return CustomSuccessResponse.withData(putUserDtoResponse);
    }

    @Override
    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo() {
        List<User> users = userRepository.findAll();
        List<PublicUserView> userViewList =  UserMapper.INSTANCE.listOfUsersToListOfPublicUserViews(users);
        return CustomSuccessResponse.withData(userViewList);
    }

    @Override
    public CustomSuccessResponse<PublicUserView> getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorsMsg.USER_NOT_FOUND));
        return CustomSuccessResponse.withData(UserMapper.INSTANCE.userToPublicUserView(user));
    }

    @Override
    public BaseSuccessResponse deleteUser(Principal principal) {
        User user = this.getUserFromPrincipal(principal);
        userRepository.deleteById(user.getId());
        return BaseSuccessResponse.ok();
    }
}
