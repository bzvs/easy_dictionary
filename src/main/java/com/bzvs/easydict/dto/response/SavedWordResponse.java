package com.bzvs.easydict.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SavedWordResponse(UUID uuid,
                                String sourceWord,
                                String targetWord,
                                LocalDateTime createdAt) {
}
