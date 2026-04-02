package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.entity.UserPushSubscriptionEntity;
import com.bzvs.easydict.repository.api.UserPushSubscriptionRepository;
import com.bzvs.easydict.service.api.UserService;
import com.bzvs.easydict.service.api.UserPushSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPushSubscriptionServiceImpl implements UserPushSubscriptionService {

    private final UserPushSubscriptionRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public void saveSubscriptionForCurrentUser(String endpoint, String p256dh, String auth) {
        UserDto currentUser = userService.getCurrentUser();
        UserPushSubscriptionEntity entity = new UserPushSubscriptionEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setUserUuid(currentUser.getUuid());
        entity.setEndpoint(endpoint);
        entity.setP256dhKey(p256dh);
        entity.setAuthKey(auth);
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        entity.setDeleted(false);
        repository.save(entity);
    }
}
