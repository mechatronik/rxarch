/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.arch.lifecycle.LiveData;

import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;


public final class RxArch {
    private RxArch() {}

    public static <T> LiveData<T> toLiveData(ObservableSource<T> upstream) {
        return new RxObservableLiveData<>(upstream);
    }

    public static <T> LiveData<T> toLiveData(SingleSource<T> upstream) {
        return new RxSingleLiveData<>(upstream);
    }

    public static <T> LiveData<T> toLiveData(MaybeSource<T> upstream) {
        return new RxMaybeLiveData<>(upstream);
    }

}
