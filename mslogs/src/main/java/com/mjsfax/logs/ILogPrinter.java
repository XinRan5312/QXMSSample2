/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: ILogPrinter.java
* Author: Hao Renjie
* Version: 1.0
* Create: 2015-12-04
*
* Changes
* ------------------------------------------------
* 2015-12-04 : 创建 ILogPrinter.java (Hao Renjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;

public interface ILogPrinter {
    void setMessageFormatter(IMessageFormatter formatter);

    void print(String message, String fileName, String position, int level);

    int getPrinterType();
}
