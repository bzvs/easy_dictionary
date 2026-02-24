package com.bzvs.easydict.service.adapter;

import com.bzvs.easydict.dto.request.TranslationRequest;
import com.bzvs.easydict.dto.response.TranslationResponse;
import com.bzvs.easydict.service.adapter.api.TranslationAdapter;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "translation.api.provider", havingValue = "google")
public class GoogleTranslationAdapter implements TranslationAdapter {

    private final Translate googleTranslate;

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        Translation translation = googleTranslate.translate(
                request.getText(),
                Translate.TranslateOption.sourceLanguage(request.getSource().getIso639Code()),
                Translate.TranslateOption.targetLanguage(request.getDestination().getIso639Code())
        );
        return new TranslationResponse(translation.getTranslatedText());
    }
}
