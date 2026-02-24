package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.UserTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTranslationRepository extends JpaRepository<UserTranslationEntity, Long> {

    List<UserTranslationEntity> findByUserUuidAndDeletedFalseOrderByCreateDateDesc(UUID userUuid);

    boolean existsByUserUuidAndTranslationUuidAndDeletedFalse(UUID userUuid, UUID translationUuid);

    Optional<UserTranslationEntity> findByUuidAndUserUuidAndDeletedFalse(UUID uuid, UUID userUuid);
}
