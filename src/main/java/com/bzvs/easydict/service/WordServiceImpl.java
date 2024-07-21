package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.Language;
import com.bzvs.easydict.dto.WordDto;
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
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }

    @Override
    public WordDto findByValue(String value, Language language) {
        return mapper.mapToDto(repository.findByValueAndLanguage(value, language));
    }

    @Override
    public Collection<WordDto> findByUuids(Collection<UUID> uuids) {
        return mapper.mapToDtos(repository.findByUuidIn(uuids));
    }
}
