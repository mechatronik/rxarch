/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;


public final class RxArchPlugins {
    private RxArchPlugins() {}

    @Nullable private static Consumer<Throwable> errorHandler = null;

    @Nullable public static Consumer<Throwable> getErrorHandler() {
        return errorHandler;
    }

    public static void setErrorHandler(@Nullable Consumer<Throwable> errorHandler) {
        RxArchPlugins.errorHandler = errorHandler;
    }

    public static void reset() {
        setErrorHandler(null);
    }

    static void onError(@NonNull Throwable error) {
        if (error == null) {
            throw new NullPointerException("Null error passed to errorHandler() in RxArch");
        }
        if (errorHandler == null) {
            throw new OnErrorNotImplementedException("Unhandled error in RxArch", error);
        }
        try {
            errorHandler.accept(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
