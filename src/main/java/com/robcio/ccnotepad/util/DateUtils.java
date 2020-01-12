package com.robcio.ccnotepad.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date addDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
