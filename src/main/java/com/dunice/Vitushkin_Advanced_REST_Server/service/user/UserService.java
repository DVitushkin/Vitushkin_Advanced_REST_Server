package com.dunice.Vitushkin_Advanced_REST_Server.service.user;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.PutUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.models.User;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PutUserDtoResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public CustomSuccessResponse<PublicUserView> getUserInfo(User user);

    public CustomSuccessResponse<PutUserDtoResponse> putUserInfo(User user, PutUserDto request);

    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo();

    public CustomSuccessResponse<PublicUserView> getUserById(UUID id);

    public BaseSuccessResponse deleteUser(User user);

}
