package com.bzvs.easydict.service;

import com.bzvs.easydict.entity.UserPushSubscriptionEntity;
import com.bzvs.easydict.entity.UserSettingsEntity;
import com.bzvs.easydict.repository.api.UserPushSubscriptionRepository;
import com.bzvs.easydict.repository.api.UserSettingsRepository;
import com.bzvs.easydict.service.api.NotificationSenderService;
import com.bzvs.easydict.service.api.NotificationService;
import com.bzvs.easydict.service.api.UserTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final UserSettingsRepository userSettingsRepository;
    private final UserPushSubscriptionRepository pushSubscriptionRepository;
    private final UserTranslationService userTranslationService;
    private final NotificationSenderService notificationSenderService;


    @Override
    @Transactional
    public void sendDueReminders() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<UserSettingsEntity> eligible = userSettingsRepository.findEligibleForReminder(today);
        for (UserSettingsEntity settings : eligible) {
            if (!isReminderTimeReached(settings.getReminderTime(), now)) {
                continue;
            }
            UUID userUuid = settings.getUserUuid();
            if (settings.isReminderOnlyIfWords()) {
                int count = userTranslationService.getWordsForReviewCount(userUuid);
                if (count <= 0) {
                    continue;
                }
                String body = formatWordsMessage(count);
                if (sendToUser(userUuid, body)) {
                    settings.setLastReminderSentDate(today);
                    userSettingsRepository.save(settings);
                }
            } else {
                if (sendToUser(userUuid, "Пора повторить слова!")) {
                    settings.setLastReminderSentDate(today);
                    userSettingsRepository.save(settings);
                }
            }
        }
    }

    private boolean isReminderTimeReached(String reminderTime, LocalTime now) {
        if (reminderTime == null || reminderTime.isBlank()) {
            return false;
        }
        try {
            LocalTime target = LocalTime.parse(reminderTime.trim(), TIME_FORMAT);
            return !now.isBefore(target);
        } catch (Exception e) {
            return false;
        }
    }

    private static String formatWordsMessage(int count) {
        if (count == 1) return "Сегодня нужно повторить 1 слово.";
        if (count >= 2 && count <= 4) return "Сегодня нужно повторить " + count + " слова.";
        return "Сегодня нужно повторить " + count + " слов.";
    }

    private boolean sendToUser(UUID userUuid, String body) {
        List<UserPushSubscriptionEntity> subs = pushSubscriptionRepository.findByUserUuidAndDeletedFalse(userUuid);
        if (subs.isEmpty()) {
            log.debug("No push subscription for user {}", userUuid);
            return false;
        }
        UserPushSubscriptionEntity sub = subs.get(0);
        return notificationSenderService.sendReminder(sub.getEndpoint(), sub.getP256dhKey(), sub.getAuthKey(), body);
    }
}
