package com.broctagon.exchangeadmin.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static LocalDate toLocalDate(String time) {
		return LocalDate.parse(time, DATE_FORMATTER);
	}
	
	public static LocalDateTime toLocalDateTime(String time) {
		return LocalDateTime.parse(time, DATE_TIME_FORMATTER);
	}
	
	public static String dateTimeNow() {
		return LocalDateTime.now().format(DATE_TIME_FORMATTER);
	}
	
	public static String date(Instant instant) {
		LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.of("UTC")).toLocalDate();
		return localDate.format(DATE_FORMATTER);
	}

	public static long getUTC(LocalDateTime dateTime) {
		ZonedDateTime localDateTime = dateTime.atZone(ZoneId.of("UTC"));
		return localDateTime.toInstant().getEpochSecond();
	}
}
