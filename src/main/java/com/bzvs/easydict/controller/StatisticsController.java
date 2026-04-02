package com.bzvs.easydict.controller;

import com.bzvs.easydict.dto.response.StatisticsResponse;
import com.bzvs.easydict.service.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<StatisticsResponse> getStatistics(
            @RequestParam(defaultValue = "30") int periodDays) {
        return ResponseEntity.ok(statisticsService.getStatisticsForCurrentUser(periodDays));
    }
}
