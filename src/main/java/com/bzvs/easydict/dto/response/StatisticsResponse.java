package com.bzvs.easydict.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticsResponse {

    /** Всего сохранённых слов */
    private long totalWords;

    /** По статусам */
    private long newWords;
    private long inProgressWords;
    private long learnedWords;

    /** Изучено за выбранный период (слов переведено в LEARNED за последние periodDays дней) */
    private long learnedInPeriod;

    /** Период в днях, за который посчитано learnedInPeriod */
    private int periodDays;

    /** Результаты тренировок за период: всего верных/неверных */
    private long trainingCorrect;
    private long trainingIncorrect;

    /** По дням (за последние periodDays): дата, верно, неверно */
    private List<DayStats> byDay;

    @Data
    @Builder
    public static class DayStats {
        private String date; // yyyy-MM-dd
        private long correct;
        private long incorrect;
    }
}
