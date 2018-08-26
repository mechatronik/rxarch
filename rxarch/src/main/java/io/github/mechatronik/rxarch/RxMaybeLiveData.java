/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.support.annotation.NonNull;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;


final class RxMaybeLiveData<T> extends DisposeOnInactiveLiveData<T> {
    private final MaybeSource<T> upstream;

    RxMaybeLiveData(@NonNull MaybeSource<T> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void onActive() {
        upstream.subscribe(new MaybeObserver<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(T t) {
                postValue(t);
                reset();
            }

            @Override
            public void onError(Throwable e) {
                RxArchPlugins.onError(e);
                reset();
            }

            @Override
            public void onComplete() {
                reset();
            }

            private void reset() {
                disposable = null;
            }
        });
    }
}
