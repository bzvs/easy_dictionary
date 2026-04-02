package com.bzvs.easydict.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "web-push")
public class WebPushProperties {

    /** VAPID public key (base64url) — передаётся клиенту для подписки */
    private String publicKey = "";

    /** VAPID private key (base64url) — для подписи запросов с бэкенда */
    private String privateKey = "";

    public boolean isConfigured() {
        return publicKey != null && !publicKey.isBlank() && privateKey != null && !privateKey.isBlank();
    }
}
