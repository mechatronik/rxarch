/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;


final class RxMaybeLiveData<T> extends LiveData<T> {
    private final MaybeSource<T> upstream;
    private Disposable disposable = null;

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
            }

            @Override
            public void onError(Throwable e) {
                RxArchPlugins.onError(e);
            }

            @Override
            public void onComplete() {}
        });
    }

    @Override
    protected void onInactive() {
        disposable.dispose();
    }
}
