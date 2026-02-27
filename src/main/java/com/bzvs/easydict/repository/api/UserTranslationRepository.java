package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.dto.UserTranslationStatus;
import com.bzvs.easydict.entity.UserTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTranslationRepository extends JpaRepository<UserTranslationEntity, Long> {

    List<UserTranslationEntity> findByUserUuidAndDeletedFalseOrderByCreateDateDesc(UUID userUuid);

    @Query("SELECT u FROM user_translation u WHERE u.userUuid = :userUuid AND u.deleted = false AND u.status = :status " +
            "AND (u.nextReviewAt IS NULL OR u.nextReviewAt <= :before)")
    List<UserTranslationEntity> findForReviewByUserAndStatusAndDue(
            @Param("userUuid") UUID userUuid,
            @Param("status") UserTranslationStatus status,
            @Param("before") LocalDateTime before);

    boolean existsByUserUuidAndTranslationUuidAndDeletedFalse(UUID userUuid, UUID translationUuid);

    Optional<UserTranslationEntity> findByUuidAndUserUuidAndDeletedFalse(UUID uuid, UUID userUuid);
}
