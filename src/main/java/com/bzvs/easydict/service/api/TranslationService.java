package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.TranslationRequest;
import com.bzvs.easydict.dto.TranslationResponse;

public interface TranslationService {

    TranslationResponse translate(TranslationRequest request);
}
