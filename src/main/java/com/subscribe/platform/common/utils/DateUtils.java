package com.subscribe.platform.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import com.subscribe.platform.common.exception.InvalidDateTypeException;

import jdk.vm.ci.meta.Local;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

	public static DayOfWeek convertStringToDayOfWeek(String weekStr) {
		switch (weekStr) {
			case "월":
				return DayOfWeek.MONDAY;
			case "화":
				return DayOfWeek.TUESDAY;
			case "수":
				return DayOfWeek.WEDNESDAY;
			case "목":
				return DayOfWeek.THURSDAY;
			case "금":
				return DayOfWeek.FRIDAY;
			case "토":
				return DayOfWeek.SATURDAY;
			case "일":
				return DayOfWeek.SUNDAY;

			default:
				throw new InvalidDateTypeException();
		}

	}

	public static LocalDate calculateHolyDay(LocalDate date) {

		while (isHolyDay(date)) {
			date = date.plusDays(1L);
		}

		return date;
	}

	private static boolean isHolyDay(LocalDate date) {

		int year = LocalDate.now().getYear();

		return
			date.isEqual(LocalDate.of(year, 1, 1))
			||	date.isEqual(LocalDate.of(year, 8, 15))
			||	date.isEqual(LocalDate.of(year, 12, 25))
			||	date.isEqual(LocalDate.of(year, 10, 3))
			||	date.isEqual(LocalDate.of(year, 10, 9))
			||	date.isEqual(LocalDate.of(year, 5, 5))
			||	date.isEqual(LocalDate.of(year, 6, 6))
			||	date.isEqual(LocalDate.of(year, 3, 1))
			|| date.getDayOfWeek() == DayOfWeek.SATURDAY
			|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

}
