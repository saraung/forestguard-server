package com.example.forestguard.auth.dto;

public record RegisterRequest(
        String email,
        String password
) {}
