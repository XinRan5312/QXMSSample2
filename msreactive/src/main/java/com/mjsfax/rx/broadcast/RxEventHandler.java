package com.mjsfax.rx.broadcast;


import com.mjsfax.rx.obervable.MSObservable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lenovo on 2017/3/3.
 */

public class RxEventHandler {
    private static volatile RxEventHandler sInstance;
    private final RxBus mBroadcast = new RxBus(RxBus.Subjects.PUBLISH);
    private final Map<Class, RxBus> mMapPublishers = new ConcurrentHashMap<>();

    static {
        sInstance = new RxEventHandler();
    }

    private RxEventHandler() {
    }

    public static RxEventHandler getInstance() {
        return sInstance;
    }

    public <T> MSObservable<T> toBroadcast(Class<T> eventType) {
        return mBroadcast.toObservable(eventType);
    }

    public final <T> MSObservable<T> toPublisher(Class<T> eventType) {
        RxBus rxBus = mMapPublishers.get(eventType);
        if (rxBus == null) {
            rxBus = new RxBus(RxBus.Subjects.BEHAVIOR);
            mMapPublishers.put(eventType, rxBus);
        }
        return rxBus.toObservable(eventType);
    }

    public void broadcast(Object object) {
        mBroadcast.post(object);
    }

    public final void publish(Object object) {
        Class cls = object.getClass();
        RxBus rxBus = mMapPublishers.get(cls);
        if (rxBus == null) {
            rxBus = new RxBus(RxBus.Subjects.BEHAVIOR);
            mMapPublishers.put(cls, rxBus);
        }
        rxBus.post(object);
    }

    public final <T> void cancelPublisher(Class<T> eventType) {
        if (mMapPublishers.containsKey(eventType)) {
            mMapPublishers.remove(eventType);
        }
    }
}
