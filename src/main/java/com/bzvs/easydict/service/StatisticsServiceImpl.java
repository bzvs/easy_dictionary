package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.UserDto;
import com.bzvs.easydict.dto.UserTranslationStatus;
import com.bzvs.easydict.dto.response.StatisticsResponse;
import com.bzvs.easydict.entity.ReviewEventEntity;
import com.bzvs.easydict.repository.api.ReviewEventRepository;
import com.bzvs.easydict.repository.api.UserTranslationRepository;
import com.bzvs.easydict.service.api.StatisticsService;
import com.bzvs.easydict.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UserService userService;
    private final UserTranslationRepository userTranslationRepository;
    private final ReviewEventRepository reviewEventRepository;

    @Override
    public StatisticsResponse getStatisticsForCurrentUser(int periodDays) {
        UserDto currentUser = userService.getCurrentUser();
        UUID userUuid = currentUser.getUuid();

        int safePeriod = Math.max(1, Math.min(365, periodDays));
        LocalDateTime periodStart = LocalDateTime.now().minusDays(safePeriod);

        long total = userTranslationRepository.countByUserUuidAndDeletedFalse(userUuid);
        long inProgress = userTranslationRepository.countByUserUuidAndStatusAndDeletedFalse(userUuid, UserTranslationStatus.IN_PROCESS);
        long learned = userTranslationRepository.countByUserUuidAndStatusAndDeletedFalse(userUuid, UserTranslationStatus.LEARNED);
        long newWords = total - inProgress - learned;

        LocalDateTime learnedFrom = LocalDateTime.now().minusDays(safePeriod);
        long learnedInPeriod = userTranslationRepository.countByUserUuidAndDeletedFalseAndStatusAndLearnedAtGreaterThanEqual(
                userUuid, UserTranslationStatus.LEARNED, learnedFrom);

        List<Object[]> rawByDay = reviewEventRepository.countByDayForUserFrom(userUuid, periodStart);
        long trainingCorrect = 0;
        long trainingIncorrect = 0;
        List<StatisticsResponse.DayStats> byDay = new ArrayList<>();
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Object[] row : rawByDay) {
            Object d = row[0];
            Number c = (Number) row[1];
            Number i = (Number) row[2];
            long correct = c != null ? c.longValue() : 0;
            long incorrect = i != null ? i.longValue() : 0;
            trainingCorrect += correct;
            trainingIncorrect += incorrect;
            String dateStr = d instanceof java.sql.Date ? ((java.sql.Date) d).toLocalDate().format(dateFmt)
                    : (d instanceof LocalDate ? ((LocalDate) d).format(dateFmt) : String.valueOf(d));
            byDay.add(StatisticsResponse.DayStats.builder()
                    .date(dateStr)
                    .correct(correct)
                    .incorrect(incorrect)
                    .build());
        }

        return StatisticsResponse.builder()
                .totalWords(total)
                .newWords(newWords)
                .inProgressWords(inProgress)
                .learnedWords(learned)
                .learnedInPeriod(learnedInPeriod)
                .periodDays(safePeriod)
                .trainingCorrect(trainingCorrect)
                .trainingIncorrect(trainingIncorrect)
                .byDay(byDay)
                .build();
    }
}
