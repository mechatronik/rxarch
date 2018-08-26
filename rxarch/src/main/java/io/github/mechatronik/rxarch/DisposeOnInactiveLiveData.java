/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.arch.lifecycle.LiveData;

import io.reactivex.disposables.Disposable;


abstract class DisposeOnInactiveLiveData<T> extends LiveData<T> {
    protected Disposable disposable = null;

    @Override
    protected void onInactive() {
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.disposable = null;
    }
}
