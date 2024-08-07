package com.bzvs.easydict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private UUID uuid;

    private String value;
    private Language language;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
