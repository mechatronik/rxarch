/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;


final class RxSingleLiveData<T> extends DisposeOnInactiveLiveData<T> {
    private final SingleSource<T> upstream;

    RxSingleLiveData(@NonNull SingleSource<T> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void onActive() {
        upstream.subscribe(new SingleObserver<T>() {
            @Override
            public void onSubscribe(Disposable d) {
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

            private void reset() {
            }
        });
    }
}
