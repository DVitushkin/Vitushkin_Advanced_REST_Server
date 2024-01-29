package com.dunice.Vitushkin_Advanced_REST_Server.service.user;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.PutUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PutUserDtoResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;

public interface UserService {
    CustomSuccessResponse<PublicUserView> getUserInfo(Principal principal);

    CustomSuccessResponse<PutUserDtoResponse> putUserInfo(Principal principal, PutUserDto request);

    CustomSuccessResponse<List<PublicUserView>> getAllUserInfo();

    CustomSuccessResponse<PublicUserView> getUserById(UUID id);

    BaseSuccessResponse deleteUser(Principal principal);

}
