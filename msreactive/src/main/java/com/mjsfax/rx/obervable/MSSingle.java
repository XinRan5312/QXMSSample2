package com.mjsfax.rx.obervable;


import com.mjsfax.logs.MSLogger;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class MSSingle<T> extends DisposableSingleObserver<T> {
    private Single<T> mTarget;
    private MSSingleObserverDisposable mDisposable;

    public MSSingle(Single<T> single) {
        mTarget = single;
    }

    public MSDisposable subscribe(DisposableSingleObserver<T> observer) {
        mDisposable = new MSSingleObserverDisposable();
        mDisposable.mObserver = observer;
        mDisposable.mDisposable = mTarget.subscribeWith(this);
        return mDisposable;
    }

    public MSDisposable subscribeWith(DisposableSingleObserver<T> observer) {
        mDisposable = new MSSingleObserverDisposable();
        mDisposable.mObserver = observer;
        mDisposable.mDisposable = mTarget.subscribeWith(this);
        return mDisposable;
    }

    public CompositeDisposable subscribe() {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mTarget.subscribe());
        return disposable;
    }

    @Override
    public void onError(Throwable e) {
        if (mDisposable != null && mDisposable.mObserver != null) {
            mDisposable.mObserver.onError(e);
        } else {
            MSLogger.v("Observer doesn't exist, drop the on error message.");
        }
    }

    @Override
    public void onSuccess(T value) {
        if (mDisposable != null && mDisposable.mObserver != null) {
            mDisposable.mObserver.onSuccess(value);
        } else {
            MSLogger.v("Observer doesn't exist, drop the on success message.");
        }
    }

    private static class MSSingleObserverDisposable<T> extends MSDisposable {
        DisposableSingleObserver<T> mObserver;

        @Override
        public void dispose() {
            super.dispose();
            mObserver = null;
        }
    }
}
