package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.request.TranslationRequest;
import com.bzvs.easydict.dto.response.TranslationResponse;

public interface TranslationService {

    TranslationResponse translate(TranslationRequest request);
}
