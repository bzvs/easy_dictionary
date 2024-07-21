package com.bzvs.easydict.mapper;

import java.util.Collection;

public interface BaseMapper<DTO, ENTITY> {

    DTO mapToDto(ENTITY entity);

    ENTITY mapToEntity(DTO dto);

    Collection<DTO> mapToDtos(Collection<ENTITY> entities);
}
