package com.memo.studygroup.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by KHAN on 2015-07-06.
 */
public class DateUtil {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static String format(Date date) {
        return format(DEFAULT_DATE_FORMAT, date);
    }

    public static String format(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
        return dateFormat.format(date);
    }
}
