package com.bzvs.easydict.entity;

import com.bzvs.easydict.dto.UserTranslationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity(name = "user_translation")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTranslationEntity extends BaseEntity {

    private UUID translationUuid;

    private UUID userUuid;

    @Enumerated(EnumType.STRING)
    private UserTranslationStatus status;

}
