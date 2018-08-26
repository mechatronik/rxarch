/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.support.annotation.NonNull;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


final class RxObservableLiveData<T> extends DisposeOnInactiveLiveData<T> {
    private final ObservableSource<T> upstream;

    RxObservableLiveData(@NonNull ObservableSource<T> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void onActive() {
        upstream.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(T t) {
                postValue(t);
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
