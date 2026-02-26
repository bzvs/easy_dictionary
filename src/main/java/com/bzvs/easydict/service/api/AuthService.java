package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.request.LoginRequest;
import com.bzvs.easydict.dto.request.SignUpRequest;
import com.bzvs.easydict.dto.response.LoginResponse;

public interface AuthService {

    UserDto signup(SignUpRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refresh(String refreshToken);
}
