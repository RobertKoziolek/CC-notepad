package com.robcio.ccnotepad.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    final static private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    final static private SimpleDateFormat shortFormat = new SimpleDateFormat("dd-MM");

    public static Date addDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date addYear(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    public static String format(final Date date) {
        return format.format(date);
    }

    public static String shortFormat(final Date date) {
        return shortFormat.format(date);
    }
}
