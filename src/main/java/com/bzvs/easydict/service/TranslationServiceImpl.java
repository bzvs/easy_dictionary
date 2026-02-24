package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.*;
import com.bzvs.easydict.dto.request.TranslationRequest;
import com.bzvs.easydict.dto.response.TranslationResponse;
import com.bzvs.easydict.entity.TranslationEntity;
import com.bzvs.easydict.mapper.TranslationMapper;
import com.bzvs.easydict.repository.api.TranslationRepository;
import com.bzvs.easydict.service.adapter.api.TranslationAdapter;
import com.bzvs.easydict.service.api.TranslationService;
import com.bzvs.easydict.service.api.UserService;
import com.bzvs.easydict.service.api.UserTranslationService;
import com.bzvs.easydict.service.api.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationAdapter translationAdapter;
    private final TranslationRepository repository;
    private final TranslationMapper mapper;
    private final WordService wordService;
    private final UserTranslationService userTranslationService;
    private final UserService userService;

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        WordDto wordForTranslation = wordService.findByValue(request.getText(), request.getSource());
        if (Objects.isNull(wordForTranslation)) {
            wordForTranslation = wordService.add(WordDto.builder()
                    .value(request.getText())
                    .language(request.getSource())
                    .createDate(LocalDateTime.now()) // annotation @CreatedDate doesn't work
                    .build());
        }

        WordDto translatedWord = findTranslationByUuid(wordForTranslation.getUuid(), request.getDestination());

        if (Objects.isNull(translatedWord)) {
            translatedWord = addNewTranslation(request, wordForTranslation);
        } else {
            ensureUserTranslationSaved(wordForTranslation.getUuid(), translatedWord.getUuid());
        }

        return TranslationResponse.builder()
                .translation(translatedWord.getValue())
                .build();
    }


    private WordDto addNewTranslation(TranslationRequest request, WordDto wordForTranslation) {
        WordDto translatedWord;
        TranslationResponse translated = translationAdapter.translate(request);
        translatedWord = wordService.add(WordDto.builder()
                .value(translated.translation())
                .language(request.getDestination())
                .createDate(LocalDateTime.now()) // annotation @CreatedDate doesn't work
                .build());
        TranslationEntity savedTranslation = repository.save(mapper.mapToEntity(TranslationDto.builder()
                .source(wordForTranslation.getUuid())
                .destination(translatedWord.getUuid())
                .createDate(LocalDateTime.now()) // annotation @CreatedDate doesn't work
                .build()));
        userTranslationService.save(UserTranslationDto.builder()
                .translationUuid(savedTranslation.getUuid())
                .userUuid(userService.getCurrentUser().getUuid())
                .status(UserTranslationStatus.NEW)
                .createDate(LocalDateTime.now()) // annotation @CreatedDate doesn't work
                .build());
        return translatedWord;
    }

    private void ensureUserTranslationSaved(UUID sourceWordUuid, UUID destinationWordUuid) {
        UUID userUuid = userService.getCurrentUser().getUuid();
        Optional<TranslationEntity> translationOpt = repository.findBySourceAndDestination(sourceWordUuid, destinationWordUuid);
        if (translationOpt.isEmpty() || userTranslationService.existsByUserAndTranslation(userUuid, translationOpt.get().getUuid())) {
            return;
        }

        userTranslationService.save(UserTranslationDto.builder()
                .translationUuid(translationOpt.get().getUuid())
                .userUuid(userUuid)
                .status(UserTranslationStatus.NEW)
                .createDate(LocalDateTime.now())
                .build());
    }

    private WordDto findTranslationByUuid(UUID uuid, Language language) {
        List<UUID> translationUuids = repository.findTranslation(uuid)
                .stream()
                .map(TranslationEntity::getDestination)
                .toList();

        return wordService.findByUuids(translationUuids)
                .stream()
                .filter(Objects::nonNull)
                .filter(word -> language.equals(word.getLanguage()))
                .findFirst()
                .orElse(null);
    }
}
