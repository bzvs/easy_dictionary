package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.UserPushSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserPushSubscriptionRepository extends JpaRepository<UserPushSubscriptionEntity, Long> {

    List<UserPushSubscriptionEntity> findByUserUuidAndDeletedFalse(UUID userUuid);
}
