package com.bzvs.easydict.service.api;

public interface UserPushSubscriptionService {

    void saveSubscriptionForCurrentUser(String endpoint, String p256dh, String auth);
}
