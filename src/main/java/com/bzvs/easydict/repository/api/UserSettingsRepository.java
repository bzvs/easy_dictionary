package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.UserSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSettingsRepository extends JpaRepository<UserSettingsEntity, Long> {

    Optional<UserSettingsEntity> findByUserUuidAndDeletedFalse(UUID userUuid);

    /** Для планировщика: настройки с включёнными уведомлениями, которым ещё не отправляли напоминание сегодня */
    @Query("SELECT s FROM user_settings s WHERE s.deleted = false AND s.notificationsEnabled = true " +
            "AND (s.lastReminderSentDate IS NULL OR s.lastReminderSentDate < :today)")
    List<UserSettingsEntity> findEligibleForReminder(@Param("today") LocalDate today);
}
