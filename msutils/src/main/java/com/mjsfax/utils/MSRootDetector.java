/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSRootDetector.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-07-11
*
* Changes
* ------------------------------------------------
* 2016-07-11 : 创建  MSRootDetector.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import java.io.File;

public class MSRootDetector {
    public static boolean isRoot() {
        if (MSRootDetector.checkBuildTags()) {
            return true;
        }

        if (MSRootDetector.checkSuperUser()) {
            return true;
        }

        if (MSRootDetector.checkSuBinary()) {
            return true;
        }

        return false;
    }

    private static boolean checkBuildTags() {
        String buildTags = android.os.Build.TAGS;
        return (buildTags != null && buildTags.contains("test-keys"));
    }

    private static boolean checkSuperUser() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkSuBinary() {
        if (new MSExecShell().executeCommand(MSExecShell.SHELL_CMD.check_su_binary) != null) {
            return true;
        } else {
            return false;
        }
    }
}
