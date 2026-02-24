package com.bzvs.easydict.translationapi;

import com.bzvs.easydict.translationapi.nlp.NlpRequest;
import com.bzvs.easydict.translationapi.nlp.NlpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "NLPApiClient",
        url = "${translation.api.nlp.url}",
        configuration = com.bzvs.easydict.config.NlpFeignConfig.class
)
public interface NLPApiClient {

    @PostMapping(value = "translation", consumes = "application/json", produces = "application/json")
    NlpResponse translate(NlpRequest request);

}
