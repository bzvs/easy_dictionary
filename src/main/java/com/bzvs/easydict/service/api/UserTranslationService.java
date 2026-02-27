package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.dto.UserTranslationStatus;
import com.bzvs.easydict.dto.response.SavedWordResponse;

import java.util.List;
import java.util.UUID;

public interface UserTranslationService {

    UserTranslationDto save(UserTranslationDto dto);

    List<SavedWordResponse> getSavedWordsForCurrentUser(UserTranslationStatus statusFilter);

    /** Слова для повторения на сегодня (IN_PROCESS, next_review_at null или <= сейчас), в случайном порядке */
    List<SavedWordResponse> getWordsForReviewToday();

    void submitReviewResult(UUID userTranslationUuid, boolean remembered);

    void setStatus(UUID userTranslationUuid, UserTranslationStatus status);

    void deleteSavedTranslation(UUID userTranslationUuid);

    boolean existsByUserAndTranslation(UUID userUuid, UUID translationUuid);
}
