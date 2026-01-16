package com.example.forestguard.auth.dto;

public record LoginRequest(
        String email,
        String password
) {}
