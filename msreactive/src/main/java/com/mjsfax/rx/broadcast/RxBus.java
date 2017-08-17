/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: RxBus.java
* Author: haorenjie
* Version: 1.0
* Create: 2017-03-01
*
* Changes
* ------------------------------------------------
* 2017-03-01 : 创建  RxBus.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.rx.broadcast;


import com.mjsfax.rx.obervable.MSObservable;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


class RxBus {
    private final Subject<Object> bus;

    RxBus(Subjects subjects) {
        switch (subjects) {
            case PUBLISH:
                bus = PublishSubject.create();
                break;
            case BEHAVIOR:
                bus = BehaviorSubject.create();
                break;
            default:
                bus = PublishSubject.create();
        }
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> MSObservable<T> toObservable(Class<T> eventType) {
        return new MSObservable<>(bus.ofType(eventType));
    }

    public enum Subjects {
        PUBLISH(PublishSubject.class),
        BEHAVIOR(BehaviorSubject.class);
        Class cls;

        Subjects(Class cls) {
            this.cls = cls;
        }
    }
}
