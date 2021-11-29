package com.subscribe.platform.services.dto;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

@Getter
public enum DateType {

    YEAR("year") {
        @Override
        public LocalDate startDay(String date) {
            return LocalDate.of(Integer.parseInt(date), 1, 1);
        }

        @Override
        public LocalDate endDay(String date) {
            return LocalDate.of(Integer.parseInt(date), 12, 31);
        }
    },
    MONTH("month") {
        @Override
        public LocalDate startDay(String date) {

            int year = LocalDate.now().getYear();

            return LocalDate.of(year, Integer.parseInt(date), 1);
        }

        @Override
        public LocalDate endDay(String date) {
            int year = LocalDate.now().getYear();
            int month = Integer.parseInt(date);
            return LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());

        }
    },
    WEEK("week") {
        @Override
        public LocalDate startDay(String date) {

            LocalDate localDate = getLocalDate(date);

            return localDate.with(DayOfWeek.MONDAY);

        }

        @Override
        public LocalDate endDay(String date) {
            LocalDate localDate = getLocalDate(date);

            return localDate.with(DayOfWeek.SUNDAY);
        }
    },
    DAY("day") {
        @Override
        public LocalDate startDay(String date) {
            return null;
        }

        @Override
        public LocalDate endDay(String date) {
            return null;
        }
    },
    ;

    private static LocalDate getLocalDate(String date) {
        int year = LocalDate.now().getYear();

        LocalDate firstDay = LocalDate.of(year, 1, 1);
        DayOfWeek firstWeekFiled = firstDay.getDayOfWeek();

        int week = Integer.parseInt(date);

        int dayOfYear;
        if (firstWeekFiled == DayOfWeek.FRIDAY || firstWeekFiled == DayOfWeek.SATURDAY) {
            dayOfYear = week * 7 + 1;
        } else {
            dayOfYear = (week - 1) * 7 + 1;
        }

        return LocalDate
                .ofYearDay(year, dayOfYear);
    }

    private String value;

    DateType(String value) {
        this.value = value;
    }

    public static DateType toKey(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }


    public abstract LocalDate startDay(String date);

    public abstract LocalDate endDay(String date);

}
