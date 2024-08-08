package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.mapper.UserTranslationMapper;
import com.bzvs.easydict.repository.api.UserTranslationRepository;
import com.bzvs.easydict.service.api.UserTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTranslationServiceImpl implements UserTranslationService {

    private final UserTranslationRepository repository;
    private final UserTranslationMapper mapper;


    @Override
    public UserTranslationDto save(UserTranslationDto dto) {
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }
}
