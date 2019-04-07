package com.robcio.ccnotepad.util;

import com.robcio.ccnotepad.CcNotepadApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger LOGGER = LoggerFactory.getLogger(CcNotepadApplication.class);

    public static void debug(final String format, Object... arguments) {
        LOGGER.debug(format, arguments);
    }

    public static void debug(final Class clazz, final String format, Object... arguments) {
        LoggerFactory.getLogger(clazz).debug(format, arguments);
    }

    public static void error(final String format, Object... arguments) {
        LOGGER.error(format, arguments);
    }

    public static void error(final Class clazz, final String format, Object... arguments) {
        LoggerFactory.getLogger(clazz).error(format, arguments);
    }
}
