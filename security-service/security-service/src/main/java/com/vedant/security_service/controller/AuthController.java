package com.vedant.security_service.controller;

import com.vedant.security_service.dto.LoginRequest;
import com.vedant.security_service.dto.LoginResponse;
import com.vedant.security_service.dto.UserRegistrationRequest;
import com.vedant.security_service.entity.User;
import com.vedant.security_service.exception.InvalidCredentialsException;
import com.vedant.security_service.jwt.JwtUtil;
import com.vedant.security_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationRequest registrationRequest) {

        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .role(registrationRequest.getRole())
                .build();

        User savedUser = userService.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        return userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())
                .map(user -> {
                    String token = jwtUtil.generateToken(user);

                    LoginResponse response = new LoginResponse(token, user.getRole().name());

                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
    }

}