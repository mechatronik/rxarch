/*
 * Copyright © 2018 Konrad Kowalewski
 *
 * This code is licensed under MIT license (see LICENSE for details)
 */

package io.github.mechatronik.rxarch;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;

/**
 * Created by Konrad Kowalewski.
 */
public class RxArchErrorHandlerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        RxArchPlugins.reset();
    }

    @Test
    public void testCallHandler() {
        SpyErrorHandler errorHandler = new SpyErrorHandler();
        Throwable error = new Throwable("Some error");
        RxArchPlugins.setErrorHandler(errorHandler);
        RxArchPlugins.onError(error);
        errorHandler.verify(1).verifyLast(error);
    }

    @Test
    public void testThrowIfThrowableIsNull() {
        expectedException.expect(isA(NullPointerException.class));
        expectedException.expectMessage("Null error passed to errorHandler() in RxArch");
        RxArchPlugins.onError(null);
    }

    @Test
    public void testThrowIfThrowableIsAnError() {
        expectedException.expect(isA(UnknownError.class));
        RxArchPlugins.onError(new UnknownError());
    }

    @Test
    public void testThrowIfThereIsNoHandlerSet() {
        Throwable error = new Throwable();
        expectedException.expect(isA(OnErrorNotImplementedException.class));
        expectedException.expectMessage("Unhandled error in RxArch");
        expectedException.expectCause(equalTo(error));
        RxArchPlugins.onError(error);
    }

    @Test
    public void testThrowIfErrorHandlerThrows() {
        RuntimeException error = new RuntimeException();
        Consumer<Throwable> handler = throwable -> {
            throw error;
        };
        RxArchPlugins.setErrorHandler(handler);
        expectedException.expect(isA(RuntimeException.class));
        expectedException.expectMessage("Exception in errorHandler");
        expectedException.expectCause(equalTo(error));
        RxArchPlugins.onError(error);
    }
}
