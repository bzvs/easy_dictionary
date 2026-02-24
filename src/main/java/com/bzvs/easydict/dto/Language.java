package com.bzvs.easydict.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {

    RUSSIAN("rus_Cyrl", "ru"),
    ENGLISH("eng_Latn", "en");

    /**
     * Код для NLP Cloud (NLLB).
     */
    private final String apiString;
    /**
     * Код ISO 639-1 для Google Cloud Translation API.
     */
    private final String iso639Code;
}
