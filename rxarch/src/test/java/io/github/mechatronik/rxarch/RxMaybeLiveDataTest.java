/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.exceptions.OnErrorNotImplementedException;

import static org.hamcrest.CoreMatchers.isA;


public class RxMaybeLiveDataTest {
    private LiveData<Integer> liveData;
    private SpyObserver<Integer> spyObserver = new SpyObserver<>();

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldEmitSingleValueFromSource() {
        liveData = new RxMaybeLiveData<>(Maybe.just(1));
        liveData.observeForever(spyObserver);
        spyObserver.assertValues(1);
    }

    @Test
    public void shouldNotEmitAnyValueFromEmptySource() {
        liveData = new RxMaybeLiveData<>(Maybe.empty());
        liveData.observeForever(spyObserver);
        spyObserver.assertValues();
    }

    @Test
    public void shouldCallErrorHandlerWhenSet() {
        SpyErrorHandler spyErrorHandler = new SpyErrorHandler();
        RxArchPlugins.setErrorHandler(spyErrorHandler);
        liveData = new RxMaybeLiveData<>(Maybe.<Integer>error(new RuntimeException("Error")));
        liveData.observeForever(spyObserver);
        spyObserver.assertValues();
        spyErrorHandler.verify(1).verifyLast(new RuntimeException("Error"));
        RxArchPlugins.reset();
    }

    @Test
    public void shouldReThrowExceptionsWhenErrorHandlerNotSet() {
        expectedException.expectCause(isA(OnErrorNotImplementedException.class));
        liveData = new RxMaybeLiveData<>(Maybe.<Integer>error(new RuntimeException("Error")));
        liveData.observeForever(spyObserver);
        spyObserver.assertValues();
    }

}