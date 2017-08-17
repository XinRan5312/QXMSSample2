package com.mjsfax.rx.obervable;


import com.mjsfax.logs.MSLogger;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class MSObservable<T> extends DisposableObserver<T> {
    private Observable<T> mTargetObservable;
    private Predicate<T> mPredicate;
    private MSDisposableObserver mNewObserver;
    private MSConsumerDisposable mNewConsumer;

    public MSObservable(Observable<T> observable) {
        mTargetObservable = observable;
    }

    public MSDisposable subscribeWith(DisposableObserver<T> observer) {
        mNewObserver = new MSDisposableObserver<>();
        mNewObserver.mObserver = observer;
        mNewObserver.mPredicate = mPredicate;
        mNewObserver.mDisposable = mTargetObservable.subscribeWith(this);
        return mNewObserver;
    }

    public MSDisposable subscribe(Consumer<T> onNext) {
        mNewConsumer = new MSConsumerDisposable<>();
        mNewConsumer.mConsumer = onNext;
        mNewConsumer.mPredicate = mPredicate;
        mNewConsumer.mDisposable = mTargetObservable.subscribeWith(this);
        return mNewConsumer;
    }

    public void subscribe() {
        mTargetObservable.subscribe();
    }

    public MSObservable<T> filter(Predicate<T> predicate) {
        mPredicate = predicate;
        if (mNewObserver != null) {
            mNewObserver.mPredicate = predicate;
        } else if (mNewConsumer != null) {
            mNewConsumer.mPredicate = predicate;
        }
        mTargetObservable = mTargetObservable.filter(new Predicate<T>() {
            @Override
            public boolean test(T t) throws Exception {
                if (mPredicate != null) {
                    return mPredicate.test(t);
                }
                return false;
            }
        });
        return this;
    }

    public MSObservable<T> observeOn(Scheduler scheduler) {
        mTargetObservable = mTargetObservable.observeOn(scheduler);
        return this;
    }

    public MSObservable<T> doOnError(Consumer<Throwable> onError) {
        mTargetObservable = mTargetObservable.doOnError(onError);
        return this;
    }

    public MSObservable<T> doOnComplete(Action onComplete) {
        mTargetObservable = mTargetObservable.doOnComplete(onComplete);
        return this;
    }

    @Override
    public void onNext(T value) {
        if (mNewObserver != null && mNewObserver.mObserver != null) {
            mNewObserver.mObserver.onNext(value);
        } else if (mNewConsumer != null && mNewConsumer.mConsumer != null) {
            try {
                mNewConsumer.mConsumer.accept(value);
            } catch (Exception e) {
                MSLogger.e(e.getMessage());
            }
        } else {
            MSLogger.v("Observer doesn't exist, drop the on next message.");
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mNewObserver != null && mNewObserver.mObserver != null) {
            mNewObserver.mObserver.onError(e);
        }  else {
            MSLogger.v("Observer doesn't exist, drop the on error message.");
        }
    }

    @Override
    public void onComplete() {
        if (mNewObserver != null && mNewObserver.mObserver != null) {
            mNewObserver.mObserver.onComplete();
        }  else {
            MSLogger.v("Observer doesn't exist, drop the on complete message.");
        }
    }

    private static class MSDisposableObserver<T> extends MSDisposable {
        DisposableObserver<T> mObserver;
        Predicate mPredicate;

        @Override
        public void dispose() {
            super.dispose();
            mObserver = null;
            mPredicate = null;
        }
    }

    private static class MSConsumerDisposable<T> extends MSDisposable {
        Consumer<T> mConsumer;
        Predicate mPredicate;

        @Override
        public void dispose() {
            super.dispose();
            mConsumer = null;
            mPredicate = null;
        }
    }
}
