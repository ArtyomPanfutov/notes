package com.panfutov.notes.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DailyPlanDto(
        Long id,
        String name,
        LocalDate targetDate,
        LocalDateTime createdTimestamp,
        LocalDateTime updatedTimestamp,
        List<DailyTaskDto> tasks) {

    public static DailyPlanDto newDailyPlan(String name, LocalDate targetDate, List<DailyTaskDto> tasks) {
        return new DailyPlanDto(null,name, targetDate, null, null, tasks);
    }
}
