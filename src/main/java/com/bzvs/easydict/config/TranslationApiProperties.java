package com.bzvs.easydict.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "translation.api")
public class TranslationApiProperties {

    /**
     * Провайдер API перевода: nlp (NLP Cloud) или google (Google Cloud Translation API).
     * Задаётся переменной окружения TRANSLATION_API_PROVIDER.
     */
    private String provider = "nlp";

    private Nlp nlp = new Nlp();

    @Getter
    @Setter
    public static class Nlp {
        /**
         * URL NLP Cloud API. Переменная окружения: TRANSLATION_NLP_URL.
         */
        private String url = "https://api.nlpcloud.io/v1/nllb-200-3-3b";
        /**
         * Токен авторизации NLP Cloud. Переменная окружения: TRANSLATION_NLP_TOKEN.
         */
        private String token = "";
    }
}
