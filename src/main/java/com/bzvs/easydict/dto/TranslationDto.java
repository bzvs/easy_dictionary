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
public class TranslationDto {

    private UUID uuid;
    private UUID source;
    private UUID destination;
    private LocalDateTime createDate;
    private LocalDateTime updateTime;
}
