package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.dto.UserTranslationStatus;
import com.bzvs.easydict.dto.response.SavedWordResponse;

import java.util.List;
import java.util.UUID;

public interface UserTranslationService {

    UserTranslationDto save(UserTranslationDto dto);

    List<SavedWordResponse> getSavedWordsForCurrentUser(UserTranslationStatus statusFilter);

    void setStatus(UUID userTranslationUuid, UserTranslationStatus status);

    void deleteSavedTranslation(UUID userTranslationUuid);

    boolean existsByUserAndTranslation(UUID userUuid, UUID translationUuid);
}
