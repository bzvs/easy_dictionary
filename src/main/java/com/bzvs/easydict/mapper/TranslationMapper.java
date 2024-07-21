package com.bzvs.easydict.mapper;

import com.bzvs.easydict.dto.TranslationDto;
import com.bzvs.easydict.entity.TranslationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationMapper extends BaseMapper<TranslationDto, TranslationEntity> {
}
