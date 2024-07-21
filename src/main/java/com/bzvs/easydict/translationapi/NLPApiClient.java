package com.bzvs.easydict.translationapi;

import com.bzvs.easydict.translationapi.nlp.NlpRequest;
import com.bzvs.easydict.translationapi.nlp.NlpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "NLPApiClient", url = "https://api.nlpcloud.io/v1/nllb-200-3-3b")
public interface NLPApiClient {

    @PostMapping(value = "translation", consumes = "application/json", produces = "application/json")
    NlpResponse translate(NlpRequest request);

}
