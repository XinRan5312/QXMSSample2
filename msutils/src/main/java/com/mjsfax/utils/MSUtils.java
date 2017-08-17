/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSMD5Utils.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-04-20
*
* Changes
* ------------------------------------------------
* 2016-04-20 : 创建  MSMD5Utils.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Locale;

public class MSUtils {
    /**
     * MD5加密 生成32位md5码
     *
     * @param inStr 待加密字符串
     * @return 返回32位md5码
     * @throws Exception
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 校验md5
     *
     * @param packagePath
     * @param filemd5
     * @return
     */
    public static boolean verifyDownloadPackage(String packagePath, String filemd5) {
        try {
            MessageDigest sig = MessageDigest.getInstance("MD5");
            File packageFile = new File(packagePath);
            InputStream signedData = new FileInputStream(packageFile);
            byte[] buffer = new byte[4096];
            long toRead = packageFile.length();
            long soFar = 0;
            boolean interrupted = false;
            while (soFar < toRead) {
                interrupted = Thread.interrupted();
                if (interrupted)
                    break;
                int read = signedData.read(buffer);
                soFar += read;
                sig.update(buffer, 0, read);
            }
            byte[] digest = sig.digest();
            String digestStr = bytesToHexString(digest);// 将得到的MD5值进行移位转换
            digestStr = digestStr.toLowerCase(Locale.getDefault());
            filemd5 = filemd5.toLowerCase(Locale.getDefault());
            if (digestStr.equals(filemd5)) {// 比较两个文件的MD5值，如果一样则返回true
                return true;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return false;
    }

    /**
     * bytesToHexString MD5值移位转换
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        int i = 0;
        while (i < src.length) {
            int v;
            String hv;
            v = (src[i] >> 4) & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            v = src[i] & 0x0F;
            hv = Integer.toHexString(v);
            stringBuilder.append(hv);
            i++;
        }
        return stringBuilder.toString();
    }
}
