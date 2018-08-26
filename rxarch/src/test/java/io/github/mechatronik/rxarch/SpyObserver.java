/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;


final class SpyObserver<T> implements Observer<T> {
    private List<T> values = new LinkedList<>();

    @Override
    public void onChanged(@Nullable T value) {
        values.add(value);
    }

    @SafeVarargs
    final void assertValues(T... values) {
        assertArrayEquals(values, this.values.toArray());
    }
}
