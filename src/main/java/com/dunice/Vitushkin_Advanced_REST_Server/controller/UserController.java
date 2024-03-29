package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.dunice.Vitushkin_Advanced_REST_Server.dto.user.PutUserDto;
import com.dunice.Vitushkin_Advanced_REST_Server.exception.ErrorsMsg;
import com.dunice.Vitushkin_Advanced_REST_Server.response.BaseSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.response.PutUserDtoResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.user.UserService;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<PublicUserView>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUserInfo());
    }

    @GetMapping("/info")
    public ResponseEntity<CustomSuccessResponse<PublicUserView>> getUserInfo(Principal principal) {
        return ResponseEntity.ok(userService.getUserInfo(principal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<PublicUserView>> getUserById(
            @Valid @Positive(message = ErrorsMsg.MAX_UPLOAD_SIZE_EXCEEDED)@PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping
    public ResponseEntity<CustomSuccessResponse<PutUserDtoResponse>> putUserinfo(
            Principal principal,
            @Valid @RequestBody PutUserDto request
    ) {
        return ResponseEntity.ok(userService.putUserInfo(principal, request));
    }

    @DeleteMapping
    public ResponseEntity<BaseSuccessResponse> deleteUser(Principal principal) {
        return ResponseEntity.ok(userService.deleteUser(principal));
    }
}
