package com.bzvs.easydict.service;

import com.bzvs.easydict.config.properties.WebPushProperties;
import com.bzvs.easydict.service.api.NotificationSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderServiceImpl implements NotificationSenderService {

    private final WebPushProperties webPushProperties;
    private PushService pushService;

    @PostConstruct
    private void initPushService() {
        if (!webPushProperties.isConfigured()) {
            log.debug("Web Push VAPID not configured, skip send");
        }
        try {
            pushService = new PushService(
                    webPushProperties.getPublicKey(),
                    webPushProperties.getPrivateKey()
            );
        } catch (GeneralSecurityException e) {
            log.warn("Web Push security error: {}", e.getMessage());
        }
    }

    @Override
    public boolean sendReminder(String endpoint, String p256dh, String auth, String body) {
        if (!Objects.isNull(pushService)) {
            log.debug("Push service not configured, skip send");
            return true;
        }
        try {
            String payload = "{\"title\":\"EasyDict\",\"body\":\"" + escapeJson(body) + "\"}";
            Notification notification = new Notification(endpoint, p256dh, auth, payload);
            pushService.send(notification);
            return true;
        } catch (Exception e) {
            log.warn("Web Push send failed: {}", e.getMessage());
            return false;
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
