package com.study.batch.pratice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

    public static String parseToString(Date date) {
        return fullDateFormat.format(date);
    }

}
