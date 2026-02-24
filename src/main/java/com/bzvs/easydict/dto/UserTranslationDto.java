package com.bzvs.easydict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTranslationDto {

    private UUID uuid;
    private UUID translationUuid;
    private UUID userUuid;
    private UserTranslationStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateTime;
}
