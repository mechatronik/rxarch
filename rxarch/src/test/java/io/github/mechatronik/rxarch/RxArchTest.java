package io.github.mechatronik.rxarch;

import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by Konrad Kowalewski.
 */
public class RxArchTest {

    @Test
    public void shouldReturnRxSingleLiveDataFromSingle() {
        assertThat(Single.just(1).to(RxArch::toLiveData), instanceOf(RxSingleLiveData.class));
    }

    @Test
    public void shouldReturnRxMaybeLiveDataFromMaybe() {
        assertThat(Maybe.just(1).to(RxArch::toLiveData), instanceOf(RxMaybeLiveData.class));
    }

    @Test
    public void shouldReturnRxObservableLiveDataFromObservable() {
        assertThat(Observable.just(1).to(RxArch::toLiveData), instanceOf(RxObservableLiveData.class));
    }
}