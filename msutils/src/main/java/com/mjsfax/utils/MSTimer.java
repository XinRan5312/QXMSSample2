package com.mjsfax.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/6/17.
 */
public class MSTimer {
    public static final int INTERVAL = 1000;
    private static final String TAG = "MSTimer";
    private final static Set<WeakReference<TimerCallBack>> sCallBacks = new HashSet<>();
    private static Handler sTimerHandler = new Handler(Looper.getMainLooper());
    private static boolean sIsOnTimer = false;
    private static Runnable sRun = new Runnable() {
        @Override
        public void run() {
            sIsOnTimer = true;
            Set<WeakReference<TimerCallBack>> callBacks;
            synchronized (sCallBacks) {
                callBacks = new HashSet<>(sCallBacks);
            }
            if (callBacks.size() == 0) {
                sIsOnTimer = false;
                sTimerHandler.removeCallbacks(sRun);
                Log.d(TAG, "Call backs in runnable");
            } else {
                for (WeakReference<TimerCallBack> callBack : callBacks) {
                    if (callBack.get() != null) {
                        callBack.get().onTimer();
                    } else {
                        Log.d(TAG, "Call back has been released.");
                    }
                }
                sTimerHandler.postDelayed(this, INTERVAL);
            }
        }
    };

    public static void start(TimerCallBack callBack) {
        synchronized (sCallBacks) {
            if (!MSTimer.contains(callBack)) {
                sCallBacks.add(new WeakReference<>(callBack));
            }
        }
        if (sIsOnTimer) {
            Log.d(TAG, "Is onTimer");
        } else {
            sIsOnTimer = true;
            sTimerHandler.post(sRun);
        }
    }

    public static void cancel(TimerCallBack callBack) {
        synchronized (sCallBacks) {
            for (WeakReference<TimerCallBack> item : sCallBacks) {
                if (item.get() != null && item.get() == callBack) {
                    sCallBacks.remove(item);
                    break;
                }
            }
        }
        if (sIsOnTimer) {
            Log.d(TAG, "Is on Timer");
        } else {
            sTimerHandler.removeCallbacks(sRun);
        }
    }

    public interface TimerCallBack {
        void onTimer();
    }

    private static  boolean contains(TimerCallBack callback) {
        for (WeakReference<TimerCallBack> item : sCallBacks) {
            if (item.get() != null && item.get() == callback) {
                return true;
            }
        }
        return false;
    }
}
