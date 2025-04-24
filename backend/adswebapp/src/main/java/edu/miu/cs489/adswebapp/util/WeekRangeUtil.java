package edu.miu.cs489.adswebapp.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class WeekRangeUtil {
    public static LocalDateTime getStartOfWeek(LocalDateTime dateTime) {
        return dateTime.toLocalDate()
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                        .atStartOfDay();
    }

    public static LocalDateTime getEndOfWeek(LocalDateTime dateTime) {
        return dateTime.toLocalDate()
                        .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                        .atTime(LocalTime.MAX);
    }
}
