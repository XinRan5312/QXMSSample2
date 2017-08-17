/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSTextUtils.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-05-30
*
* Changes
* ------------------------------------------------
* 2016-05-30 : 创建  MSTextUtils.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MSTextUtils {
    public static List<String> digitalsFromText(String text) {
        List<String> digitals = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            digitals.add(matcher.group());
        }
        return digitals;
    }

    public static int parserBeginDigital(String text) {
        Pattern pattern = Pattern.compile("^[0-9]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return 0;
    }
}
