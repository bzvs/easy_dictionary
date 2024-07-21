package com.bzvs.easydict.service.adapter;

import com.bzvs.easydict.dto.TranslationRequest;
import com.bzvs.easydict.dto.TranslationResponse;
import com.bzvs.easydict.service.adapter.api.TranslationAdapter;
import com.bzvs.easydict.translationapi.NLPApiClient;
import com.bzvs.easydict.translationapi.nlp.NlpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslationAdapterImpl implements TranslationAdapter {

    private final NLPApiClient nlpApiClient;


    @Override
    public TranslationResponse translate(TranslationRequest request) {
        return new TranslationResponse(nlpApiClient.translate(
                        new NlpRequest(request.getText(),
                                request.getSource().getApiString(),
                                request.getDestination().getApiString()))
                .translation_text());
    }
}
