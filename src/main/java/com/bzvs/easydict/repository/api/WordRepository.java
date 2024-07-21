package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.dto.Language;
import com.bzvs.easydict.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    WordEntity findByValueAndLanguage(String value, Language language);

    Collection<WordEntity> findByUuidIn(Collection<UUID> uuids);
}
