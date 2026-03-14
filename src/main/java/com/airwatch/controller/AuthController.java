package com.airwatch.controller;

import com.airwatch.dto.request.LoginRequest;
import com.airwatch.dto.request.RegisterRequest;
import com.airwatch.dto.response.ApiResponse;
import com.airwatch.dto.response.AuthResponse;
import com.example.airwatch.dto.request.*;
import com.example.airwatch.dto.response.*;
import com.airwatch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(
            @Valid @RequestBody RegisterRequest req) {
        return ApiResponse.success(userService.register(req));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {
        return ApiResponse.success(userService.login(req));
    }
}