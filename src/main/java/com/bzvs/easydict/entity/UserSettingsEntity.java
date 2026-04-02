package com.bzvs.easydict.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "user_settings")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingsEntity extends BaseEntity {

    @Column(name = "user_uuid", nullable = false, unique = true)
    private UUID userUuid;

    @Column(name = "notifications_enabled", nullable = false)
    private boolean notificationsEnabled = false;

    /** Время напоминания в формате HH:mm (например 09:00) */
    @Column(name = "reminder_time", length = 5)
    private String reminderTime = "09:00";

    @Column(name = "reminder_only_if_words", nullable = false)
    private boolean reminderOnlyIfWords = true;

    /** Дата последней отправки напоминания (чтобы не слать дважды в день) */
    @Column(name = "last_reminder_sent_date")
    private LocalDate lastReminderSentDate;
}
