package com.dunice.Vitushkin_Advanced_REST_Server.controller;

import com.dunice.Vitushkin_Advanced_REST_Server.response.CustomSuccessResponse;
import com.dunice.Vitushkin_Advanced_REST_Server.service.UserService;
import com.dunice.Vitushkin_Advanced_REST_Server.views.PublicUserView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<PublicUserView>>> getAllUsers(Principal connectedUser) {
        return ResponseEntity.ok(userService.getAllUserInfo());
    }

    @GetMapping("/info")
    public ResponseEntity<CustomSuccessResponse<PublicUserView>> getUserInfo(Principal connectedUser) {
        return ResponseEntity.ok(userService.getUserInfo(connectedUser));
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomSuccessResponse<?>> getUserById(
            Principal connectedUser,
            @Valid @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}