package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.request.PushSubscriptionRequest;
import com.bzvs.easydict.dto.request.UserSettingsRequest;
import com.bzvs.easydict.dto.response.UserSettingsResponse;
import com.bzvs.easydict.service.api.UserSettingsService;
import com.bzvs.easydict.service.api.UserPushSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService userSettingsService;
    private final UserPushSubscriptionService pushSubscriptionService;

    @GetMapping
    public ResponseEntity<UserSettingsResponse> getSettings() {
        return ResponseEntity.ok(userSettingsService.getSettingsForCurrentUser());
    }

    @PutMapping
    public ResponseEntity<UserSettingsResponse> updateSettings(@RequestBody UserSettingsRequest request) {
        return ResponseEntity.ok(userSettingsService.updateSettingsForCurrentUser(request));
    }

    @PostMapping("push-subscription")
    public ResponseEntity<Void> savePushSubscription(@RequestBody PushSubscriptionRequest request) {
        if (request == null || request.getEndpoint() == null || request.getP256dh() == null || request.getAuth() == null) {
            return ResponseEntity.badRequest().build();
        }
        pushSubscriptionService.saveSubscriptionForCurrentUser(request.getEndpoint(), request.getP256dh(), request.getAuth());
        return ResponseEntity.noContent().build();
    }
}
