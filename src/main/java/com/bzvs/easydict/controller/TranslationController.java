package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.TranslationRequest;
import com.bzvs.easydict.dto.TranslationResponse;
import com.bzvs.easydict.service.api.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("translation")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService service;

    @PostMapping("word")
    public TranslationResponse translate(@RequestBody TranslationRequest request) {
        return service.translate(request);
    }
}
