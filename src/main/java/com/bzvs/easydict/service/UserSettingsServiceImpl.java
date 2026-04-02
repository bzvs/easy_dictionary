package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.config.properties.WebPushProperties;
import com.bzvs.easydict.dto.request.UserSettingsRequest;
import com.bzvs.easydict.dto.response.UserSettingsResponse;
import com.bzvs.easydict.entity.UserSettingsEntity;
import com.bzvs.easydict.repository.api.UserSettingsRepository;
import com.bzvs.easydict.service.api.UserService;
import com.bzvs.easydict.service.api.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private static final String DEFAULT_REMINDER_TIME = "09:00";

    private final UserSettingsRepository repository;
    private final UserService userService;
    private final WebPushProperties webPushProperties;

    @Override
    public UserSettingsResponse getSettingsForCurrentUser() {
        UserSettingsEntity entity = getOrCreateSettings();
        return toResponse(entity);
    }

    @Override
    @Transactional
    public UserSettingsResponse updateSettingsForCurrentUser(UserSettingsRequest request) {
        UserDto currentUser = userService.getCurrentUser();
        UserSettingsEntity entity = repository.findByUserUuidAndDeletedFalse(currentUser.getUuid())
                .orElseGet(() -> createNewSettings(currentUser.getUuid()));

        if (request.getNotificationsEnabled() != null) {
            entity.setNotificationsEnabled(request.getNotificationsEnabled());
        }
        if (request.getReminderTime() != null && !request.getReminderTime().isBlank()) {
            entity.setReminderTime(request.getReminderTime());
        }
        if (request.getReminderOnlyIfWords() != null) {
            entity.setReminderOnlyIfWords(request.getReminderOnlyIfWords());
        }
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
        return toResponse(entity);
    }

    private UserSettingsEntity getOrCreateSettings() {
        UserDto currentUser = userService.getCurrentUser();
        return repository.findByUserUuidAndDeletedFalse(currentUser.getUuid())
                .orElseGet(() -> {
                    UserSettingsEntity created = createNewSettings(currentUser.getUuid());
                    return repository.save(created);
                });
    }

    private UserSettingsEntity createNewSettings(UUID userUuid) {
        UserSettingsEntity entity = new UserSettingsEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setUserUuid(userUuid);
        entity.setNotificationsEnabled(false);
        entity.setReminderTime(DEFAULT_REMINDER_TIME);
        entity.setReminderOnlyIfWords(true);
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        entity.setDeleted(false);
        return entity;
    }

    private UserSettingsResponse toResponse(UserSettingsEntity entity) {
        UserSettingsResponse.UserSettingsResponseBuilder b = UserSettingsResponse.builder()
                .notificationsEnabled(entity.isNotificationsEnabled())
                .reminderTime(entity.getReminderTime() != null ? entity.getReminderTime() : DEFAULT_REMINDER_TIME)
                .reminderOnlyIfWords(entity.isReminderOnlyIfWords());
        if (webPushProperties.isConfigured()) {
            b.vapidPublicKey(webPushProperties.getPublicKey());
        }
        return b.build();
    }
}
