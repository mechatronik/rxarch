/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;


final class RxSingleLiveData<T> extends LiveData<T> {
    private final SingleSource<T> upstream;
    private Disposable disposable = null;

    RxSingleLiveData(@NonNull SingleSource<T> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void onActive() {
        upstream.subscribe(new SingleObserver<T>() {
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
        });
    }

    @Override
    protected void onInactive() {
        disposable.dispose();
    }
}
