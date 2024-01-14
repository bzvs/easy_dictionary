package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.WordDto;
import com.bzvs.easydict.service.api.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("word")
@RequiredArgsConstructor
public class WordController {

    private final WordService service;

    public ResponseEntity<WordDto> add(WordDto dto) {
        return ResponseEntity.ok(service.add(dto));
    }
}
