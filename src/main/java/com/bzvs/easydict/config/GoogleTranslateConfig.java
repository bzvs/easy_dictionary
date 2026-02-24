package com.bzvs.easydict.config;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Создаёт клиент Google Translate при provider=google.
 * Использует GOOGLE_APPLICATION_CREDENTIALS (путь к JSON сервисного аккаунта).
 */
@Configuration
@ConditionalOnProperty(name = "translation.api.provider", havingValue = "google")
public class GoogleTranslateConfig {

    @Bean
    public Translate googleTranslate() {
        return TranslateOptions.getDefaultInstance().getService();
    }
}
