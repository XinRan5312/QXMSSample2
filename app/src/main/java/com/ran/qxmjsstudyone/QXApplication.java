package com.ran.qxmjsstudyone;

import android.app.Application;
import android.content.Context;

/**
 * Created by houqixin on 2017/6/28.
 */

public class QXApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
//        DaggerAppComponent.builder().qxCommonModleFactory(new QxCommonModleFactory(this)).build();
    }
    public static Context getAppContext(){
        return mContext;
    }
}
