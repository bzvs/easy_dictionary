package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.entity.UserEntity;
import com.bzvs.easydict.mapper.UserMapper;
import com.bzvs.easydict.repository.api.UserRepository;
import com.bzvs.easydict.service.api.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private static final String BAD_CREDENTIALS_MESSAGE = "Bad credentials or unregistered user";

    @Override
    public UserDto findByEmail(String email) {
        UserDto user = mapper.mapToDto(repository.findByEmail(email));
        if (Objects.isNull(user)) {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }

        return initializeDetailsFields(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        UserEntity entity = mapper.mapToEntity(userDto);
        entity.setCreateDate(LocalDateTime.now());
        entity.setDeleted(false);
        prepareEntityBeforeSave(entity);

        return mapper.mapToDto(repository.save(entity));
    }

    @Override
    public UserDto getCurrentUser() {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UserDto initializeDetailsFields(UserDto user) {
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        return user;
    }

    private void prepareEntityBeforeSave(UserEntity entity) {
        entity.setUpdateDate(LocalDateTime.now());
    }
}
