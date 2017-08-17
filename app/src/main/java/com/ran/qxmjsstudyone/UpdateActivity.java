package com.ran.qxmjsstudyone;

import android.os.Bundle;
import android.view.View;

import com.ran.qxmjsstudyone.base.BaseActvity;

import util.UpdateAppUtils;

/**
 * Created by houqixin on 2017/8/16.
 */

public class UpdateActivity extends BaseActvity{
    //服务器apk path,这里放了百度云盘的apk 作为测试
    String apkPath = "http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.15.1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    public void updateApp(View view) {
        UpdateAppUtils.from(this)
                .serverVersionCode(2)
                .serverVersionName("2.0")
                .apkPath(apkPath)
                .update();
    }

    public void downloadByWeb(View view) {
        UpdateAppUtils.from(this)
                .serverVersionCode(2)
                .serverVersionName("2.0")
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .update();
    }


    public void forceUpdate(View view) {
        UpdateAppUtils.from(this)
                .serverVersionCode(2)
                .serverVersionName("2.0")
                .apkPath(apkPath)
                .isForce(true)
                .update();
    }

    public void checkByName(View view) {
        UpdateAppUtils.from(this)
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME)
                .serverVersionName("2.0")
                .serverVersionCode(2)
                .apkPath(apkPath)
                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER)
                .isForce(true)
                .update();
    }
}
