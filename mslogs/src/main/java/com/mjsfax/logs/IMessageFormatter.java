/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: IMessageFormatter.java
* Author: haorenjie
* Version: 1.0
* Create: 2015-12-13
*
* Changes
* ------------------------------------------------
* 2015-12-13 : 创建  IMessageFormatter.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;

public interface IMessageFormatter {
    public String format(String message, String fileName, String position, int level);
}
