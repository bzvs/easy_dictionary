package com.bzvs.easydict.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/** Web Push подписка пользователя для отправки уведомлений с бэкенда */
@Entity(name = "user_push_subscription")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPushSubscriptionEntity extends BaseEntity {

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "endpoint", nullable = false, length = 2048)
    private String endpoint;

    @Column(name = "p256dh_key", nullable = false, length = 512)
    private String p256dhKey;

    @Column(name = "auth_key", nullable = false, length = 256)
    private String authKey;
}
