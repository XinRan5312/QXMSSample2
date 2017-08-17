/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSFileManager.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-04-20
*
* Changes
* ------------------------------------------------
* 2016-04-20 : 创建  MSFileManager.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lenovo on 2016/1/21.
 */
public class MSFileManager {
    //数据库的存储目录
    public static final String STATISTICS_DIR_NAME = "statistics";
    public static final String STACK_TRACE = "trace.txt";
    private static final long LOG_EXPIRED_TIME_INTERVAL = 3 * 24 * 60 * 60 * 1000; // 文件有效期3 days
    //默认存储路径根目录
    private static final String EXTERNAL_ROOT_DIR_NAME = "mjs";
    //默认的Log缓存的路径
    private static final String LOG_DIR_NAME = "logs";
    //图像的缓存目录
    private static final String IMAGE_DIR_NAME = "images";
    //缓存目录,如Json数据
    private static final String CACHE_DIR_NAME = "cache";
    //下载目录
    private static final String DOWNLOAD_DIR_NAME = "downloads";
    private static final String FEEDBACK_DIR_NAME = "feedback";
    private static final String LOG_FILE_SUFFIX = "log";
    private static final String CRASH_FILE_SUFFIX = "crash";
    private static final String PROPERTY = "prop";
    private static final String PROPERTY_SUFFIX = ".properties";
    private static final String PRO_VER = "ver_";
    //
    private static final String TAG = "FILE_MANAGER";
    //单例
    private static MSFileManager mFileManager;
    private Context mContext;

    /**
     * 构造方法
     *
     * @param context 上下文对象
     */
    private MSFileManager(Context context) {
        mContext = context;
    }

    /**
     * 获取单例对象的方法
     *
     * @param context 上下文对象
     * @return
     */
    public static MSFileManager getInstance(Context context) {
        if (mFileManager == null) {
            mFileManager = new MSFileManager(context);
        }
        return mFileManager;
    }

    /**
     * 获取外置存储卡路径
     *
     * @return 若外置存储卡路径不为空, 则返回外置存储卡根目录
     */
    private File getExternalStorageDirectory() {
        String storageState = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(storageState)) {
            return null;
        }

        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取内置文件的目录
     *
     * @return
     */
    private File getFileDirectory() {
        return mContext.getApplicationContext().getFilesDir();
    }

    public File getCacheDirectory() {
        return mContext.getApplicationContext().getCacheDir();
    }

    public File getStackTraceDirectory() {
        File internalStorageDir = getFileDirectory();
        if (internalStorageDir == null) {
            return null;
        }
        File traceFile = new File(internalStorageDir, STACK_TRACE);
        try {
            if (!traceFile.exists() && !traceFile.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, "StackTrace File cannot created");
            return null;
        }
        return traceFile;
    }

    /**
     * 获取民金所的外置的根目录
     *
     * @return
     */
    private File getMjsExternalStorageDirectory() {
        File externalStorageDir = getExternalStorageDirectory();
        if (externalStorageDir == null) {
            return null;
        }
        String packageName = mContext.getApplicationInfo().packageName;
        File externalRoot = new File(externalStorageDir, EXTERNAL_ROOT_DIR_NAME + File.separator + packageName);
        if (!externalRoot.exists() && !externalRoot.mkdirs()) {
            return null;
        }
        return externalRoot;
    }

    /**
     * 获取民金所外置的log目录
     *
     * @return
     */
    public File getMjsLogsDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, LOG_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {
            Log.e(TAG, "Create log directory failed.");
        }
        clearOldFiles(logDir, LOG_FILE_SUFFIX);
        String logName = MSTimeUtils.getTimeString("yyyy-MM-dd", new Date());
        logName = getFullName(logName + LOG_FILE_SUFFIX + ".txt");
        File logFile = new File(logDir, logName);
        return logFile;
    }

    public File getMjsCrashDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, LOG_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {
            Log.e(TAG, "Create log directory failed.");
        }
        clearOldFiles(logDir, CRASH_FILE_SUFFIX);
        String crashName = MSTimeUtils.getTimeString("yyyy-MM-dd-HH:mm:ss", new Date());
        crashName = getFullName(crashName + CRASH_FILE_SUFFIX + ".txt");
        File logFile = new File(logDir, crashName);
        return logFile;
    }

    /**
     * 获取统计模块的外置存储卡的存储路径
     *
     * @return
     */
    public File getMjsStatisticsDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File stsDir = new File(rootDir, STATISTICS_DIR_NAME);
        if (!stsDir.exists() && !stsDir.mkdir()) {
            Log.e(TAG, "Create Statistics directory failed.");
        }
        return stsDir;
    }

    /**
     * 获取民金所外置的image目录
     *
     * @return
     */
    public File getMjsImageDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, IMAGE_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {
            Log.e(TAG, "Create images directory failed.");
        }
        return logDir;
    }

    /**
     * 获取民金所外置的cache目录
     *
     * @return
     */
    public File getMjsCacheDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, CACHE_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {
            Log.e(TAG, "Create cache directory failed.");
        }
        return logDir;
    }

    /**
     * 获取民金所外置的downloads目录
     *
     * @return
     */
    public File getMjsDownloadDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, DOWNLOAD_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {
            Log.e(TAG, "Create downloads directory failed.");
        }
        return logDir;
    }

    public File getMjsFeedbackDirectory() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File feedbackDir = new File(rootDir, FEEDBACK_DIR_NAME);
        if (!feedbackDir.exists() && !feedbackDir.mkdir()) {
            Log.e(TAG, "Create feedback directory failed.");
        }
        return feedbackDir;
    }

    /**
     * 获取logs文件保存的目录
     *
     * @return
     */
    public File getLogsFileDir() {
        File rootDir = getMjsExternalStorageDirectory();
        if (rootDir == null) {
            rootDir = getFileDirectory();
        }
        File logDir = new File(rootDir, LOG_DIR_NAME);
        if (!logDir.exists() && !logDir.mkdir()) {

            return null;
        }
        return logDir;
    }

    /**
     * 根据文件后缀找出制定目录下所有的文件
     *
     * @param dir    文件目录
     * @param suffix 查找文件的后缀
     * @return
     */
    public File[] getFilsWithSuffix(File dir, final String suffix) {

        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().toString().endsWith(suffix);
            }
        });
        return files;
    }

    public void clearOldFiles(File logDir, final String suffix) {
        File[] oldLogs = logDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                try {
                    String shortName = pathname.getName();
                    int startpos = shortName.indexOf(".");
                    shortName = shortName.substring(startpos + 1);
                    int suffixPos = shortName.lastIndexOf(".");
                    String dateStr="";
                    Date date=null;
                    //新版本的log日志文件和崩溃文件均以.txt为后缀
                    if (shortName.endsWith(".txt")) {
                        if (suffix == null || !shortName.contains(suffix)) {
                            //需要注意:log日志和崩溃日志均以".txt"结尾,不做此判断,可能会造成误删
                            return false;
                        }
                        if (CRASH_FILE_SUFFIX.equals(suffix)) {
                            suffixPos -= suffix.length();
                            dateStr = shortName.substring(0, suffixPos);
                            if (dateStr == null) {
                                return false;
                            }
                           date = MSTimeUtils.convertStringToDate("yyyy-MM-dd-HH:mm:ss", dateStr);
                        }
                        if (LOG_FILE_SUFFIX.equals(suffix)) {
                            suffixPos -= suffix.length();
                            dateStr = shortName.substring(0, suffixPos);
                            if (dateStr == null) {
                                return false;
                            }
                            //兼容以秒为单位创建的log.txt文件,兼容旧版本
                            String formatStr;
                            if (dateStr.contains("_")) {
                                //兼容删除发出去的版本有"2016_11_11log.txt"的情况
                                formatStr = dateStr.contains(":") ? "yyyy_MM_dd_HH:mm:ss" : "yyyy_MM_dd";
                            } else {
                                formatStr = dateStr.contains(":") ? "yyyy-MM-dd-HH:mm:ss" : "yyyy-MM-dd";
                            }
                            date = MSTimeUtils.convertStringToDate(formatStr, dateStr);
                        }
                        if (date == null) {
                            return false;
                        }
                        Date now = new Date();
                        boolean result = (now.getTime() - date.getTime() > LOG_EXPIRED_TIME_INTERVAL);
                        return result;
                    }else{
                        //老版本中以日志文件和崩溃文件分别以log和crash结尾
                        dateStr = shortName.substring(0, suffixPos);
                        date = MSTimeUtils.convertStringToDate("yyyy_MM_dd", dateStr);
                        Date now = new Date();
                        return (now.getTime() - date.getTime() > LOG_EXPIRED_TIME_INTERVAL) && shortName.endsWith(suffix);
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });
        for (File oldLog : oldLogs) {
            Log.i(TAG, "clear:" + oldLog.toString());
            oldLog.delete();
        }
    }

    public void copyFile(File src, File des) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(des, true);
            int len = 0;
            byte[] buf = new byte[1024 * 8];
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len - 1);
                fos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        src.delete();
    }

    public File getStackTraceCacheDirectory() {
        File stackTraceCache = new File(getCacheDirectory(), STACK_TRACE);
        return stackTraceCache;
    }

    private String getFullName(String fileName) {
        int pid = android.os.Process.myPid();
        String psName = "";
        ActivityManager mActivityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                psName = appProcess.processName;
                if (psName.equals(mContext.getApplicationInfo().packageName)) {
                    psName = "main";
                } else {
                    int pos = psName.indexOf(":");
                    if (pos == -1) {
                        psName = "unknown";
                    } else {
                        psName = psName.substring(pos + 1);
                    }
                }
                break;
            }
        }
        return psName + "." + fileName;
    }

    public File getShareSdkDirectory() {
        if (null == getExternalStorageDirectory()) {
            return null;
        }
        File file = new File(getExternalStorageDirectory(), "ShareSDK");
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public File getMJSPropertyFile(Context context) {
        File file = new File(context.getCacheDir(), PROPERTY);
        if (!file.exists()) {
            file.mkdir();
        }

        int verCode = MSDeviceUtils.getVersionCode();
        clearOldProperties(file, verCode);
        File properFile = new File(file, String.format(Locale.getDefault(), "%s%d%s", PRO_VER, verCode, PROPERTY_SUFFIX));
        if (!properFile.exists()) {
            try {
                properFile.createNewFile();
            } catch (IOException e) {
                Log.e(getClass().getSimpleName().toString(), e + "");
            }
        }
        return properFile;
    }

    public void clearOldProperties(File proDir, final int versionCode) {
        File[] oldProperties = proDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                try {
                    String path = pathname.toString();
                    if (TextUtils.isEmpty(path)) {
                        return false;
                    }
                    int index = path.lastIndexOf(".");
                    if (index == -1) {
                        return false;
                    }
                    String name = path.substring(0, index);
                    int vCodeIndex = name.indexOf("_");
                    if (vCodeIndex == -1) {
                        return false;
                    }
                    int vCode = Integer.valueOf(name.substring(vCodeIndex + 1));
                    return vCode < versionCode;
                } catch (Exception e) {
                    return false;
                }
            }
        });
        for (File file : oldProperties) {
            file.delete();
        }
    }

}
