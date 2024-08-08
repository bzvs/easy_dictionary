package com.bzvs.easydict.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserTranslationStatus {

    NEW("Новая запись"),
    IN_PROCESS("В процессе запоминания"),
    LEARNED("Выучено");

    private final String description;
}
