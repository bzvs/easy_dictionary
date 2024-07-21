package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<TranslationEntity, Long> {

    @Query("Select t from Translation t where t.source = ?1")
    Collection<TranslationEntity> findTranslation(UUID sourceUuid);
}
