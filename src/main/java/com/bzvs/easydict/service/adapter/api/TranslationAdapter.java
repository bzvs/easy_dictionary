package com.bzvs.easydict.service.adapter.api;

import com.bzvs.easydict.dto.request.TranslationRequest;
import com.bzvs.easydict.dto.response.TranslationResponse;

public interface TranslationAdapter {

    TranslationResponse translate(TranslationRequest request);
}
