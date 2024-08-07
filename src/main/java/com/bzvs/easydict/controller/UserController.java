package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }
}
