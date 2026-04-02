package com.bzvs.easydict.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSettingsResponse {

    private boolean notificationsEnabled;
    private String reminderTime;
    private boolean reminderOnlyIfWords;
    /** VAPID public key для подписки на push (если настроен на бэкенде) */
    private String vapidPublicKey;
}
