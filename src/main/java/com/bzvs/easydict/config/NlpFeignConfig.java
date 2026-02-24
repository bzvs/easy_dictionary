package com.bzvs.easydict.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NlpFeignConfig {

    private final TranslationApiProperties translationApiProperties;

    @Bean
    @ConditionalOnProperty(name = "translation.api.provider", havingValue = "nlp")
    public RequestInterceptor nlpAuthTokenInterceptor() {
        String token = translationApiProperties.getNlp().getToken();
        return template -> template.header("Authorization", "Token " + token);
    }
}
