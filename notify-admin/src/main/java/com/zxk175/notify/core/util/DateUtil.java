package com.zxk175.notify.core.util;

import com.zxk175.notify.core.constant.Const;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zxk175
 * @since 2020-03-29 13:35
 */
public class DateUtil {
	
	public static String now(String format) {
		if (MyStrUtil.isBlank(format)) {
			format = Const.DATE_FORMAT_DEFAULT;
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dtf.format(LocalDateTime.now());
	}
	
	public static LocalDateTime str2LocalDateTime(String date, String format) {
		if (MyStrUtil.isBlank(format)) {
			format = Const.DATE_FORMAT_DEFAULT;
		}
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date, df);
	}
	
	private static String localDateTime2Str(LocalDateTime dateTime, String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dtf.format(dateTime);
	}
	
	public static String formatDate(Long timestamp, String format) {
		if (MyStrUtil.isBlank(format)) {
			format = Const.DATE_FORMAT_DEFAULT;
		}
		
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
		return localDateTime2Str(localDateTime, format);
	}
	
	public static Date datePlus(int type, Date date, int ttl) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, ttl);
		return calendar.getTime();
	}
	
}
