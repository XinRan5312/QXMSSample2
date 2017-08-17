package com.mjsfax.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by lenovo on 2016/5/4.
 */
public class MSToastUtils {
    public static final int INTERVAL_LONG = 5000;
    public static final int INTERVAL_SHORT = 2000;
    private static Toast mToastResId = null;
    private static Toast mToastText = null;
    private static Handler mHandler = new Handler();
    private static Runnable mRunnableRes = new Runnable() {
        @Override
        public void run() {
            if (mToastResId != null) {
                mToastResId.cancel();
            }
            mToastResId = null;
        }
    };
    private static Runnable mRunnableText = new Runnable() {
        @Override
        public void run() {
            if (mToastText != null) {
                mToastText.cancel();
            }
            mToastText = null;
        }
    };

    private MSToastUtils() {

    }

    public static synchronized void showLong(Context context, String text) {
        showText(context, text, Toast.LENGTH_LONG);
    }

    public static synchronized void showShort(Context context, String text) {
        showText(context, text, Toast.LENGTH_SHORT);
    }

    public static synchronized void showLong(Context context, int textId) {
        showText(context, textId, Toast.LENGTH_LONG);
    }

    public static synchronized void showShort(Context context, int textId) {
        showText(context, textId, Toast.LENGTH_SHORT);
    }

    public static synchronized void showText(Context context, String text, int duration) {
        mHandler.removeCallbacks(mRunnableText);
        if (mToastText == null) {
            mToastText = Toast.makeText(context, text, duration);
        }
        mHandler.postDelayed(mRunnableText, getInterval(duration));
        mToastText.show();
    }

    public static synchronized void showText(Context context, int resId, int duration) {
        mHandler.removeCallbacks(mRunnableRes);
        if (mToastResId == null) {
            mToastResId = Toast.makeText(context, resId, duration);
        }
        mHandler.postDelayed(mRunnableRes, getInterval(duration));//1s后自动将Toast置空
        mToastResId.show();
    }

    private static int getInterval(int duration) {
        int interval = 0;
        if (duration == Toast.LENGTH_SHORT) {
            interval = INTERVAL_SHORT;
        } else {
            interval = INTERVAL_LONG;
        }
        return interval;
    }

}
