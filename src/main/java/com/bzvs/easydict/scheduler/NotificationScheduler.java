package com.bzvs.easydict.scheduler;

import com.bzvs.easydict.service.api.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик: раз в 15 минут проверяет, кому нужно отправить напоминание о повторении слов,
 * и отправляет Web Push с бэкенда.
 */
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedRate = 15 * 60 * 1000) // каждые 15 минут
    public void sendDueReminders() {
        notificationService.sendDueReminders();
    }
}
