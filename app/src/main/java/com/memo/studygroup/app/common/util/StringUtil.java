package com.memo.studygroup.app.common.util;

/**
 * Created by KHAN on 2015-07-06.
 */
public class StringUtil {
    public static String nvl(String s) {
        return isEmpty(s) ? "" : s;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
