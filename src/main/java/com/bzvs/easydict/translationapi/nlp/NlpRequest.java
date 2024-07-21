package com.bzvs.easydict.translationapi.nlp;

public record NlpRequest(
        String text,
        String source,
        String target
) {
}
