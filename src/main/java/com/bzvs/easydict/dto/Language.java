package com.bzvs.easydict.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {

    RUSSIAN("rus_Cyrl"),
    ENGLISH("eng_Latn");

    private final String apiString;
}
