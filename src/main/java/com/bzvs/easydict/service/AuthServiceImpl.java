package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.request.LoginRequest;
import com.bzvs.easydict.dto.request.SignUpRequest;
import com.bzvs.easydict.dto.response.LoginResponse;
import com.bzvs.easydict.service.api.AuthService;
import com.bzvs.easydict.service.api.JwtService;
import com.bzvs.easydict.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto signup(SignUpRequest request) {
        return userService.save(UserDto.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .telegram(request.telegram())
                .build());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(), request.password()));
        UserDto user = userService.findByEmail(request.email());
        String token = jwtService.generateToken(user);


        return new LoginResponse(token, jwtService.getExpirationTime());
    }
}
