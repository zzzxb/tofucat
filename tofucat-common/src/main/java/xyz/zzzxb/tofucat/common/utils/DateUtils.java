package xyz.zzzxb.tofucat.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * zzzxb
 * 2023/8/25
 */
public final class DateUtils {

    public static String formatNowDate(String pattern) {
        return formatDate(pattern, new Date());
    }

    public static String formatDate(String pattern, Date date) {
        CheckParamUtils.isBlack(pattern).throwMessage("pattern is null");
        CheckParamUtils.isNull(date).throwMessage("date is null");

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parserDate(String pattern, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
