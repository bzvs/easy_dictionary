package com.bzvs.easydict.dto.request;

import lombok.Data;

@Data
public class UserSettingsRequest {

    private Boolean notificationsEnabled;
    /** Время в формате HH:mm */
    private String reminderTime;
    private Boolean reminderOnlyIfWords;
}
