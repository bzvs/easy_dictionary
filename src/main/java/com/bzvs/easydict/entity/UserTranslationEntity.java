package com.bzvs.easydict.entity;

import com.bzvs.easydict.dto.UserTranslationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "user_translation")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTranslationEntity extends BaseEntity {

    private UUID translationUuid;

    private UUID userUuid;

    @Enumerated(EnumType.STRING)
    private UserTranslationStatus status;

    /** Дата следующего показа по интервальному повторению (null = показывать при первом заходе) */
    private LocalDateTime nextReviewAt;

    /** Текущий интервал в днях до следующего показа */
    private Integer intervalDays;

    /** Уровень SRS 1–5 (упрощённая шкала интервалов) */
    private Integer srsLevel;

}
