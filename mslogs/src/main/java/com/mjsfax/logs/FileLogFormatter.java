/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: FileLogFormatter.java
* Author: haorenjie
* Version: 1.0
* Create: 2015-12-13
*
* Changes
* ------------------------------------------------
* 2015-12-13 : 创建  FileLogFormatter.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;


import android.os.Process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileLogFormatter implements IMessageFormatter {
    public static String getTimeString(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    @Override
    public String format(String message, String fileName, String position, int level) {
        String dateTimeStr = getTimeString("yyyy-MM-dd HH:mm:ss:SSS", new Date());
        return String.format("[%s] [%s] [%s] %s (%s:%s)%n", dateTimeStr, getRuntimeInfo(), getLogLevelTag(level), message, fileName, position);
    }

    private String getRuntimeInfo() {
        long threadID = Thread.currentThread().getId();
        int processID = Process.myPid();

        return String.format(Locale.getDefault(), "%d - %d", processID, threadID);
    }
    public String getLogLevelTag(int level) {
        switch (level) {
            case MSLoggerImpl.LogLevel.VERBOSE:
                return "V";
            case MSLoggerImpl.LogLevel.DEBUG:
                return "D";
            case MSLoggerImpl.LogLevel.INFO:
                return "I";
            case MSLoggerImpl.LogLevel.WARNING:
                return "W";
            case MSLoggerImpl.LogLevel.ERROR:
                return "E";
            default:
                return "U"; // Unknown
        }
    }
}
