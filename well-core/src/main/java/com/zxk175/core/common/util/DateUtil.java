package com.zxk175.core.common.util;

import com.zxk175.core.common.constant.Const;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author zxk175
 * @since 17/10/19 16:46
 */
public class DateUtil {

    private static final String FORMAT_DEFAULT = Const.DATE_FORMAT_DEFAULT;


    public static String now(String format) {
        if (MyStrUtil.isBlank(format)) {
            format = FORMAT_DEFAULT;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(LocalDateTime.now());
    }

    public static LocalDateTime str2LocalDateTime(String date, String format) {
        if (MyStrUtil.isBlank(format)) {
            format = FORMAT_DEFAULT;
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
            format = FORMAT_DEFAULT;
        }

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
        return localDateTime2Str(localDateTime, format);
    }
}
