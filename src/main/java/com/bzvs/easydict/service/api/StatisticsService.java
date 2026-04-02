package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.response.StatisticsResponse;

public interface StatisticsService {

    /**
     * Статистика для текущего пользователя.
     *
     * @param periodDays период в днях для «изучено за период» и для разбивки тренировок по дням (7, 30, 90)
     */
    StatisticsResponse getStatisticsForCurrentUser(int periodDays);
}
