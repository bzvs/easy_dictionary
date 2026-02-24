package com.bzvs.easydict.service.adapter;

import com.bzvs.easydict.dto.request.TranslationRequest;
import com.bzvs.easydict.dto.response.TranslationResponse;
import com.bzvs.easydict.service.adapter.api.TranslationAdapter;
import com.bzvs.easydict.translationapi.NLPApiClient;
import com.bzvs.easydict.translationapi.nlp.NlpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "translation.api.provider", havingValue = "nlp")
public class NlpTranslationAdapter implements TranslationAdapter {

    private final NLPApiClient nlpApiClient;

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        return new TranslationResponse(nlpApiClient.translate(
                        new NlpRequest(
                                request.getText(),
                                request.getSource().getApiString(),
                                request.getDestination().getApiString()))
                .translation_text());
    }
}
