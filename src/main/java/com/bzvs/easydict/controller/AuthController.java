package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.request.LoginRequest;
import com.bzvs.easydict.dto.request.RefreshTokenRequest;
import com.bzvs.easydict.dto.request.SignUpRequest;
import com.bzvs.easydict.dto.response.LoginResponse;
import com.bzvs.easydict.service.api.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(service.refresh(request.refreshToken()));
    }
}
