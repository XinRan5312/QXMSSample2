/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSNetworkUtils.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-07-11
*
* Changes
* ------------------------------------------------
* 2016-07-11 : 创建  MSNetworkUtils.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class MSNetworkUtils {
    private static final String TAG = "MSNetworkUtils";

    public static int getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NetworkType.NETWORN_NONE;
        }

        int netType = networkInfo.getType();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.NETWORN_WIFI;
        }

        if (netType == ConnectivityManager.TYPE_MOBILE) {
            int subType = networkInfo.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE
                    || subType == TelephonyManager.NETWORK_TYPE_1xRTT
                    || subType == TelephonyManager.NETWORK_TYPE_IDEN) {
                return NetworkType.NETWORN_2G;
            }

            if (subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_HSUPA
                    || subType == TelephonyManager.NETWORK_TYPE_HSPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B
                    || subType == TelephonyManager.NETWORK_TYPE_EHRPD
                    || subType == TelephonyManager.NETWORK_TYPE_HSPAP) {
                return NetworkType.NETWORN_3G;
            }

            if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
                return NetworkType.NETWORN_4G;
            }

            String subTypeName = networkInfo.getSubtypeName();
            if (subTypeName.equals("TD-SCDMA")
                    || subTypeName.equals("WCDMA")
                    || subTypeName.equals("CDMA2000")) {
                return NetworkType.NETWORN_3G;
            }

            Log.w(TAG, "Unknown mobile network type: " + subType + ", name: " + subTypeName);
            return NetworkType.NETWORN_MOBILE;
        }

        return NetworkType.NETWORN_NONE;
    }

    public static int getOperatorType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getNetworkOperator();
        if (TextUtils.isEmpty(operator)) {
            return OperatorType.OP_NONE;
        }

        if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
            return OperatorType.OP_CMCC;
        }

        if (operator.equals("46001") || operator.equals("46006")) {
            return OperatorType.OP_CUCC;
        }

        if (operator.equals("46003") || operator.equals("46005")) {
            return OperatorType.OP_CTCC;
        }

        Log.w(TAG, "Unknown operator: " + operator);
        return OperatorType.OP_UNKNOWN;
    }

    public static class NetworkType {
        public static final int NETWORN_NONE = 0;
        // wifi连接
        public static final int NETWORN_WIFI = 1;
        // 手机网络数据连接类型
        public static final int NETWORN_2G = 2;
        public static final int NETWORN_3G = 3;
        public static final int NETWORN_4G = 4;
        public static final int NETWORN_MOBILE = 5;//未知
    }

    public static class OperatorType {
        public static final int OP_NONE = 0;
        public static final int OP_CMCC = 1;
        public static final int OP_CUCC = 2;
        public static final int OP_CTCC = 3;
        public static final int OP_UNKNOWN = 4;
    }
}
