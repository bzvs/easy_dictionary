package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.Language;
import com.bzvs.easydict.dto.WordDto;
import com.bzvs.easydict.entity.WordEntity;
import com.bzvs.easydict.mapper.WordMapper;
import com.bzvs.easydict.repository.api.WordRepository;
import com.bzvs.easydict.service.api.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository repository;
    private final WordMapper mapper;

    @Override
    public WordDto add(WordDto dto) {
        WordEntity entity = mapper.mapToEntity(dto);
        if (entity.getValue() != null) {
            entity.setValue(entity.getValue().toLowerCase());
        }
        return mapper.mapToDto(repository.save(entity));
    }

    @Override
    public WordDto findByValue(String value, Language language) {
        String searchValue = value != null ? value.toLowerCase() : null;
        return mapper.mapToDto(repository.findByValueAndLanguage(searchValue, language));
    }

    @Override
    public Collection<WordDto> findByUuids(Collection<UUID> uuids) {
        return mapper.mapToDtos(repository.findByUuidIn(uuids));
    }
}
