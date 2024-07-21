package com.bzvs.easydict.mapper;

import com.bzvs.easydict.dto.WordDto;
import com.bzvs.easydict.entity.WordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper extends BaseMapper<WordDto, WordEntity> {
}
