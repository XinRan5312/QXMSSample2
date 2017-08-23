package com.ran.qxmjsstudyone.utils;

import android.util.Log;

import com.ran.qxmjsstudyone.BuildConfig;


public class LogUtils {

    public static void LogD(String tag, String msg){
//        if (BuildConfig.DEBUG)
            Log.e(tag, msg);
    }

    public static void LogE(String tag, String msg){
        if (BuildConfig.DEBUG)
            Log.e(tag, msg);
    }

}
