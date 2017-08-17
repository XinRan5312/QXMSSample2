/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSDeviceUtils.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-05-04
*
* Changes
* ------------------------------------------------
* 2016-05-04 : 创建  MSDeviceUtils.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

public class MSDeviceUtils {
    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) sContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static int getVersionCode()//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = sContext.getPackageManager().getPackageInfo(sContext.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
