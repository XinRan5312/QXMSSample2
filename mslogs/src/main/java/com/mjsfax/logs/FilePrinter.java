/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: FilePrinter.java
* Author: Hao Renjie
* Version: 1.0
* Create: 2015-12-04
*
* Changes
* ------------------------------------------------
* 2015-12-04 : 创建 FilePrinter.java (Hao Renjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;

import android.content.Context;

import com.mjsfax.utils.MSFileManager;
import com.mjsfax.utils.MSSerialQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter implements ILogPrinter {
    private static final int LOG_FLUSH_FREQ = 10;//刷新的条数
    protected static FilePrinter mPrinter;
    private IMessageFormatter mMessageFormatter;
    private BufferedWriter mBw;
    private MSSerialQueue mSerailQueue = new MSSerialQueue("LogPrinter");
    private int mMessageCounter = 0;
    private File mLogFile;
    private MSFileManager mFile;
    //mBuilder属性主要用于存储在mLogger初始化之前的已经打印的Logger日志
    private Context mContext;

    private FilePrinter(Context context) {
        mContext = context;
        mFile = MSFileManager.getInstance(mContext);
        mLogFile = mFile.getMjsLogsDirectory();
        try {
            FileWriter fw = new FileWriter(mLogFile, true);
            mBw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FilePrinter getInstance(Context context) {
        if (mPrinter == null) {
            mPrinter = new FilePrinter(context);
        }
        return mPrinter;
    }

    @Override
    public void setMessageFormatter(IMessageFormatter formatter) {
        mMessageFormatter = formatter;
    }

    @Override
    public void print(final String message, final String fileName, final String position, final int level) {
        if (mLogFile == null || !mLogFile.exists()) {
            mLogFile = mFile.getMjsLogsDirectory();
            try {
                FileWriter fw = new FileWriter(mLogFile, true);
                mBw = new BufferedWriter(fw);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final String log = mMessageFormatter.format(message, fileName, position, level);
        mSerailQueue.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mBw.write(log);
                    ++mMessageCounter;
                    //修改文件输出等级,如果遇到警告以上等级的日志应当立即输出
                    if (mMessageCounter > LOG_FLUSH_FREQ || level >= MSLoggerImpl.LogLevel.WARNING) {
                        mBw.flush();
                        mMessageCounter = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getPrinterType() {
        return MSLoggerImpl.Output.FILE;
    }

}
