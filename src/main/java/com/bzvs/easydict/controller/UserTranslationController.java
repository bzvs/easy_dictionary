package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.response.SavedWordResponse;
import com.bzvs.easydict.service.api.UserTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user/translation")
@RequiredArgsConstructor
public class UserTranslationController {

    private final UserTranslationService userTranslationService;

    @GetMapping
    public ResponseEntity<List<SavedWordResponse>> getSavedWords() {
        return ResponseEntity.ok(userTranslationService.getSavedWordsForCurrentUser());
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteSavedWord(@PathVariable UUID uuid) {
        userTranslationService.deleteSavedTranslation(uuid);
        return ResponseEntity.noContent().build();
    }
}
