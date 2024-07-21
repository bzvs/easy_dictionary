package com.bzvs.easydict.service.adapter.api;

import com.bzvs.easydict.dto.TranslationRequest;
import com.bzvs.easydict.dto.TranslationResponse;

public interface TranslationAdapter {

    TranslationResponse translate(TranslationRequest request);
}
