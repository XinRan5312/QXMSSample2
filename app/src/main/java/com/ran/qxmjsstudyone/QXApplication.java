package com.ran.qxmjsstudyone;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ran.qxmjsstudyone.base.BaseActvity;

/**
 * Created by houqixin on 2017/6/28.
 */

public class QXApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {//对应 每个Actvity的生命周期，
            //请记住是每个 都会在这里被调用 也就是说 我们就不用写BaseActivity了 如果我们已经有了BaseActvity但是 为了实现一个第三方的ku
            //我们可以让这个Actvity实现一个接口
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if(activity instanceof AppCompatActivity){
                    //可以做点工作
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
//        DaggerAppComponent.builder().qxCommonModleFactory(new QxCommonModleFactory(this)).build();
    }
    public static Context getAppContext(){
        return mContext;
    }
}
