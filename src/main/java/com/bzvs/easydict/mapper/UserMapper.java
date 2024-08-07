package com.bzvs.easydict.mapper;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, UserEntity> {
}
