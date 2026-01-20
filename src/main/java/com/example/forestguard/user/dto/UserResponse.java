package com.example.forestguard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String role;
    private boolean enabled;
    private boolean emailVerified;
}
