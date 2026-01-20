package com.example.forestguard.user.controller;

import com.example.forestguard.auth.entity.User;
import com.example.forestguard.user.dto.UserResponse;
import com.example.forestguard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Logged-in user profile
    @GetMapping("/me")
    public UserResponse getCurrentUser() {

        User user = userService.getCurrentUser();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.isEmailVerified()
        );
    }
}
