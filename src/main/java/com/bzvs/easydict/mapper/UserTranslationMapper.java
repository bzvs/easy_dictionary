package com.bzvs.easydict.mapper;

import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.entity.UserTranslationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTranslationMapper extends BaseMapper<UserTranslationDto, UserTranslationEntity> {
}
