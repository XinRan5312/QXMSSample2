/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSTimeUtils.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-04-20
*
* Changes
* ------------------------------------------------
* 2016-04-20 : 创建  MSTimeUtils.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MSTimeUtils {
    public static final String LONG_TIME = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DETAIL_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_TIME = "yyyy-MM-dd";
    private static final String TAG = "MSTimeUtils";

    public static long convertStringToDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            if (date.contains(".")) {
                return convertStringToDate(LONG_TIME, date).getTime();
            } else if (date.contains(":")) {
                return convertStringToDate(DETAIL_TIME, date).getTime();
            } else {
                return convertStringToDate(SHORT_TIME, date).getTime();
            }
        } else {
            return new Date().getTime();
        }
    }

    public static String convertDateToString(long date, boolean detail) {
        Date d = new Date(date);
        if (detail) {
            return getTimeString(DETAIL_TIME, d);
        } else {
            return getTimeString(SHORT_TIME, d);
        }
    }

    public static String getTimeString(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    public static Date convertStringToDate(String format, String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            Log.e(TAG, e + "");
        }
        return new Date();
    }

    public static String utcTs2String(String format, long utcTs) {
        return getTimeString(format, new Date(utcTs));
    }

    public static Date utcTs2Date(long utcTs) {
        return new Date(utcTs);
    }

    public static boolean isToady(long utcTs) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(utcTs));
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        return (date.get(Calendar.YEAR) == now.get(Calendar.YEAR))
                && (date.get(Calendar.MONTH) == now.get(Calendar.MONTH))
                && (date.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH));
    }
}
