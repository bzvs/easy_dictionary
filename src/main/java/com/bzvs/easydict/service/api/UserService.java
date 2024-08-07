package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.UserDto;

public interface UserService {

    UserDto findByEmail(String email);

    UserDto save(UserDto userDto);

    UserDto getCurrentUser();
}
