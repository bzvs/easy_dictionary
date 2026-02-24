package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.dto.WordDto;
import com.bzvs.easydict.dto.response.SavedWordResponse;
import com.bzvs.easydict.entity.TranslationEntity;
import com.bzvs.easydict.entity.UserTranslationEntity;
import com.bzvs.easydict.mapper.UserTranslationMapper;
import com.bzvs.easydict.repository.api.TranslationRepository;
import com.bzvs.easydict.repository.api.UserTranslationRepository;
import com.bzvs.easydict.service.api.UserService;
import com.bzvs.easydict.service.api.UserTranslationService;
import com.bzvs.easydict.service.api.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTranslationServiceImpl implements UserTranslationService {

    private final UserTranslationRepository repository;
    private final UserTranslationMapper mapper;
    private final UserService userService;
    private final TranslationRepository translationRepository;
    private final WordService wordService;

    @Override
    public UserTranslationDto save(UserTranslationDto dto) {
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }

    @Override
    public List<SavedWordResponse> getSavedWordsForCurrentUser() {
        List<SavedWordResponse> result = new ArrayList<>();
        UserDto currentUser = userService.getCurrentUser();
        List<UserTranslationEntity> userTranslations = repository.findByUserUuidAndDeletedFalseOrderByCreateDateDesc(currentUser.getUuid());
        if (CollectionUtils.isEmpty(userTranslations)) {
            return result;
        }

        Set<UUID> translationUuids = userTranslations.stream().map(UserTranslationEntity::getTranslationUuid).collect(Collectors.toSet());
        Collection<TranslationEntity> translations = translationRepository.findByUuidIn(translationUuids);
        Set<UUID> wordUuids = translations.stream()
                .flatMap(t -> java.util.stream.Stream.of(t.getSource(), t.getDestination()))
                .collect(Collectors.toSet());
        Map<UUID, WordDto> wordsByUuid = wordService.findByUuids(wordUuids).stream()
                .collect(Collectors.toMap(WordDto::getUuid, Function.identity()));

        for (UserTranslationEntity ut : userTranslations) {
            TranslationEntity trans = translations.stream()
                    .filter(t -> t.getUuid().equals(ut.getTranslationUuid()))
                    .findFirst()
                    .orElseThrow();
            WordDto source = wordsByUuid.get(trans.getSource());
            WordDto dest = wordsByUuid.get(trans.getDestination());
            if (source == null || dest == null) {
                continue;
            }

            result.add(new SavedWordResponse(
                    ut.getUuid(),
                    source.getValue(),
                    dest.getValue(),
                    ut.getCreateDate()));
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteSavedTranslation(UUID userTranslationUuid) {
        UserDto currentUser = userService.getCurrentUser();
        Optional<UserTranslationEntity> opt = repository.findByUuidAndUserUuidAndDeletedFalse(userTranslationUuid, currentUser.getUuid());
        opt.ifPresent(repository::delete);
    }

    @Override
    public boolean existsByUserAndTranslation(UUID userUuid, UUID translationUuid) {
        return repository.existsByUserUuidAndTranslationUuidAndDeletedFalse(userUuid, translationUuid);
    }
}
