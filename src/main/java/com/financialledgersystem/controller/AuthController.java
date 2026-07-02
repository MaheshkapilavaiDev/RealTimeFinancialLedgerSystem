package com.financialledgersystem.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.financialledgersystem.dto.ApiResponse;
import com.financialledgersystem.dto.AuthResponse;
import com.financialledgersystem.dto.LoginRequest;
import com.financialledgersystem.dto.RegisterRequest;
import com.financialledgersystem.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request){

        String message = authService.register(request);

        return ResponseEntity.ok(
                new ApiResponse(true,message));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(
                authService.login(request));
    }

}
