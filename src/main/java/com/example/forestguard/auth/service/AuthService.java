package com.example.forestguard.auth.service;

import com.example.forestguard.auth.dto.LoginRequest;
import com.example.forestguard.auth.dto.RegisterRequest;
import com.example.forestguard.auth.entity.User;
import com.example.forestguard.auth.repository.UserRepository;
import com.example.forestguard.auth.security.JwtUtil;
import com.example.forestguard.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public void register(RegisterRequest request) {


        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email already registered", HttpStatus.CONFLICT);
        }


        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setEnabled(true);
        user.setEmailVerified(false);

        userRepository.save(user);
    }


    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Invalid email or password", HttpStatus.UNAUTHORIZED));

        if (!user.isEnabled()) {
            throw new BusinessException("Account disabled", HttpStatus.FORBIDDEN);
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }


        return jwtUtil.generateToken(user.getEmail());
    }
}
