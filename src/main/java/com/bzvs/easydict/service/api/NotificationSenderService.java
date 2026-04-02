package com.bzvs.easydict.service.api;

/**
 * Отправка Web Push уведомления по подписке.
 * Если VAPID не настроен, методы не выполняют отправку.
 */
public interface NotificationSenderService {

    /**
     * Отправить напоминание о повторении слов на указанный endpoint (подписка пользователя).
     *
     * @param endpoint endpoint из PushSubscription
     * @param p256dh   ключ p256dh из подписки
     * @param auth     ключ auth из подписки
     * @param body     текст уведомления
     * @return true если отправка выполнена (или не настроена — не ошибка)
     */
    boolean sendReminder(String endpoint, String p256dh, String auth, String body);
}
