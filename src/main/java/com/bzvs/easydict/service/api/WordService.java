package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.Language;
import com.bzvs.easydict.dto.WordDto;

import java.util.Collection;
import java.util.UUID;

public interface WordService {

    WordDto add(WordDto dto);

    WordDto findByValue(String value, Language language);

    Collection<WordDto> findByUuids(Collection<UUID> uuids);

}
