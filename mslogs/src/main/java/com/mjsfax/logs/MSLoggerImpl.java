/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: Logger.java
* Author: Hao Renjie
* Version: 1.0
* Create: 2015-12-04
*
* Changes
* ------------------------------------------------
* 2015-12-04 : 创建 Logger.java (Hao Renjie);
* ------------------------------------------------
*/

package com.mjsfax.logs;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MSLoggerImpl {
    private static MSLoggerImpl sInstance;
    private int mPrintLevel = LogLevel.INFO;
    private int mOutput = Output.CONSOLE;
    private int mStackTraceOffset = -1;
    private boolean isDebug = true;
    private List<ILogPrinter> mLogPrinters = new ArrayList<ILogPrinter>(2);

    private MSLoggerImpl() {

    }

    public static MSLoggerImpl getsInstance() {
        if (sInstance == null) {
            sInstance = new MSLoggerImpl();
        }
        return sInstance;
    }

    public void initialize(Context context) {
        FilePrinter filePrinter = FilePrinter.getInstance(context);
        FileLogFormatter fileLogFormatter = new FileLogFormatter();
        filePrinter.setMessageFormatter(fileLogFormatter);
        mLogPrinters.add(filePrinter);
        //判断类型,只有debug版本才进行控制台日志输出
        if (isDebug) {
            ConsolePrinter consolePrinter = new ConsolePrinter();
            mLogPrinters.add(consolePrinter);
        } else {
            Log.i("BuildConfig.Debug", "false");
        }
    }

    public void initialize(Context context, boolean debug) {
        isDebug = debug;
        initialize(context);
    }

    public void setLogConfig(int printLevel, int output) {
        mPrintLevel = printLevel;
        mOutput = output;
    }

    public void verbose(String message) {
        print(message, LogLevel.VERBOSE);
    }

    public void debug(String message) {
        print(message, LogLevel.DEBUG);
    }

    public void info(String message) {
        print(message, LogLevel.INFO);
    }

    public void warning(String message) {
        print(message, LogLevel.WARNING);
    }

    public void error(String message) {
        print(message, LogLevel.ERROR);
    }

    private void print(String message, int level) {
        if (mPrintLevel > level) {
            return;
        }
        if (level == LogLevel.VERBOSE && !message.contains("[http_test]")) {
            return;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (mStackTraceOffset == -1) {
            int offset = getStackTraceOffset(stackTrace);
            mStackTraceOffset = offset == -1 ? 5 : offset;
        }
        StackTraceElement element = stackTrace[mStackTraceOffset];
        String fileName = getFileName(element);
        String position = getPosition(element);
        for (ILogPrinter logPrinter : mLogPrinters) {
            if ((logPrinter.getPrinterType() & mOutput) > 0) {
                logPrinter.print(message, fileName, position, level);
            }
        }
    }

    /**
     * @param element
     * @return 获取文件信息
     */
    private String getFileName(StackTraceElement element) {
        if (isDebug) {
//            StackTraceElement element = stackTrace[5];
            return element.getFileName();
        } else {
//            StackTraceElement element = stackTrace[5];
            String ste = element.toString();
            int pos = ste.lastIndexOf("(");
            String filename = ste.substring(0, pos);
            return filename;
        }
    }

    /**
     * @param element
     * @return 获取位置信息.返回方法名或者行信息
     */
    private String getPosition(StackTraceElement element) {
        return "at Line : " + element.getLineNumber();
    }

    private int getStackTraceOffset(StackTraceElement[] trace) {
        for (int i = 3; i < trace.length; i++) {
            StackTraceElement ele = trace[i];
            String className = ele.getClassName();
            if (!className.equals(MSLogger.class.getName()) && !className.equals(MSLoggerImpl.class.getName())) {
                return i;
            }
        }
        return -1;
    }

    public static class LogLevel {
        public static final int VERBOSE = 1;
        public static final int DEBUG = 2;
        public static final int INFO = 3;
        public static final int WARNING = 4;
        public static final int ERROR = 5;
    }

    public static class Output {
        public static final int FILE = 1;
        public static final int CONSOLE = 2;
        public static final int BOTH = FILE | CONSOLE;
    }
}
