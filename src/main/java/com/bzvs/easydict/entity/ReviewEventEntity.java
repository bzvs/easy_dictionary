package com.bzvs.easydict.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Событие повторения слова (результат тренировки: верно/неверно) для статистики
 */
@Entity(name = "review_event")
@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewEventEntity extends BaseEntity {

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "user_translation_uuid", nullable = false)
    private UUID userTranslationUuid;

    @Column(nullable = false)
    private boolean remembered;

    @Column(length = 32)
    private String mode;
}
