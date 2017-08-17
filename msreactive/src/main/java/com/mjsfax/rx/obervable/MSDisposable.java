package com.mjsfax.rx.obervable;


import io.reactivex.disposables.Disposable;

public abstract class MSDisposable implements Disposable {
    Disposable mDisposable;

    public final void clear() {
        dispose();
    }

    @Override
    public void dispose() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public final boolean isDisposed() {
        if (mDisposable != null) {
            return mDisposable.isDisposed();
        }
        return false;
    }

    public static MSDisposable empty() {
        return new MSEmptyDisposable();
    }

    private static class MSEmptyDisposable extends MSDisposable {
        // Do nothing.
    }
}
