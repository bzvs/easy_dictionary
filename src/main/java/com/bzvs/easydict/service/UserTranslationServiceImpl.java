package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.UserTranslationDto;
import com.bzvs.easydict.dto.UserTranslationStatus;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTranslationServiceImpl implements UserTranslationService {

    /** Интервалы в днях по уровням SRS (0..5). Уровень 0–1: 1 день, далее 3, 7, 14, 30 */
    private static final int[] SRS_INTERVAL_DAYS = {1, 1, 3, 7, 14, 30};
    private static final int SRS_MAX_LEVEL = 5;

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
    public List<SavedWordResponse> getSavedWordsForCurrentUser(UserTranslationStatus statusFilter) {
        UserDto currentUser = userService.getCurrentUser();
        List<UserTranslationEntity> userTranslations = repository.findByUserUuidAndDeletedFalseOrderByCreateDateDesc(currentUser.getUuid());
        if (CollectionUtils.isEmpty(userTranslations)) {
            return List.of();
        }
        if (statusFilter != null) {
            userTranslations = userTranslations.stream()
                    .filter(ut -> statusFilter.equals(ut.getStatus() != null ? ut.getStatus() : UserTranslationStatus.NEW))
                    .toList();
        }

        return buildSavedWordResponses(userTranslations);
    }

    @Override
    public List<SavedWordResponse> getWordsForReviewToday() {
        UserDto currentUser = userService.getCurrentUser();
        List<UserTranslationEntity> due = repository.findForReviewByUserAndStatusAndDue(
                currentUser.getUuid(), UserTranslationStatus.IN_PROCESS, LocalDateTime.now());
        if (CollectionUtils.isEmpty(due)) {
            return Collections.emptyList();
        }

        List<SavedWordResponse> list = buildSavedWordResponses(due);
        Collections.shuffle(list);
        return list;
    }

    @Override
    @Transactional
    public void submitReviewResult(UUID userTranslationUuid, boolean remembered) {
        UserDto currentUser = userService.getCurrentUser();
        UserTranslationEntity entity = repository.findByUuidAndUserUuidAndDeletedFalse(userTranslationUuid, currentUser.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Запись не найдена"));
        int level = entity.getSrsLevel() != null ? Math.min(entity.getSrsLevel(), SRS_MAX_LEVEL) : 0;
        if (remembered) {
            level = Math.min(SRS_MAX_LEVEL, level + 1);
            int intervalDays = SRS_INTERVAL_DAYS[Math.min(level, SRS_INTERVAL_DAYS.length - 1)];
            entity.setSrsLevel(level);
            entity.setIntervalDays(intervalDays);
            entity.setNextReviewAt(LocalDateTime.now().plusDays(intervalDays));
        } else {
            entity.setSrsLevel(0);
            entity.setIntervalDays(SRS_INTERVAL_DAYS[0]);
            entity.setNextReviewAt(LocalDateTime.now().plusDays(SRS_INTERVAL_DAYS[0]));
        }
        repository.save(entity);
    }

    private List<SavedWordResponse> buildSavedWordResponses(List<UserTranslationEntity> userTranslations) {
        List<SavedWordResponse> result = new ArrayList<>();
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
            if (source == null || dest == null) continue;
            UserTranslationStatus status = ut.getStatus() != null ? ut.getStatus() : UserTranslationStatus.NEW;
            result.add(new SavedWordResponse(
                    ut.getUuid(),
                    source.getValue(),
                    dest.getValue(),
                    ut.getCreateDate(),
                    status));
        }
        return result;
    }

    @Override
    @Transactional
    public void setStatus(UUID userTranslationUuid, UserTranslationStatus status) {
        UserDto currentUser = userService.getCurrentUser();
        UserTranslationEntity entity = repository.findByUuidAndUserUuidAndDeletedFalse(userTranslationUuid, currentUser.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Запись не найдена"));
        entity.setStatus(status);
        repository.save(entity);
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
