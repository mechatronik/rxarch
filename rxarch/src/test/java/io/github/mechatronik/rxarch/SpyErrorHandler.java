package io.github.mechatronik.rxarch;

import java.util.LinkedList;
import java.util.Queue;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.assertEquals;


final class SpyErrorHandler implements Consumer<Throwable> {
    private Queue<Throwable> invocations = new LinkedList<>();

    @Override
    public void accept(Throwable throwable) {
        invocations.add(throwable);
    }

    final SpyErrorHandler verify(int invocations) {
        assertEquals(invocations, this.invocations.size());
        return this;
    }

    final SpyErrorHandler verifyLast(Throwable throwable) {
        assertEquals(throwable.toString(), invocations.peek().toString());
        return this;
    }
}
