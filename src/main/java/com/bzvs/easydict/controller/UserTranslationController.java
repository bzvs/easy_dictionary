package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.UserTranslationStatus;
import com.bzvs.easydict.dto.request.SetWordStatusRequest;
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
    public ResponseEntity<List<SavedWordResponse>> getSavedWords(
            @RequestParam(required = false) UserTranslationStatus status) {
        return ResponseEntity.ok(userTranslationService.getSavedWordsForCurrentUser(status));
    }

    @PatchMapping("{uuid}/status")
    public ResponseEntity<Void> setWordStatus(@PathVariable UUID uuid,
                                              @RequestBody SetWordStatusRequest request) {
        if (request == null || request.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }
        userTranslationService.setStatus(uuid, request.getStatus());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> deleteSavedWord(@PathVariable UUID uuid) {
        userTranslationService.deleteSavedTranslation(uuid);
        return ResponseEntity.noContent().build();
    }
}
