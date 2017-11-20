package alvin.base.kotlin.lib.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public final class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Function0<Scheduler> subscribeOnSupplier;
    private final Function0<Scheduler> observeOnSupplier;

    private RxManager(@Nullable Function0<Scheduler> subscribeOnSupplier,
                      @Nullable Function0<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    @Nullable
    private Scheduler getSubscribeOn() {
        if (subscribeOnSupplier == null) {
            return null;
        }
        return Preconditions.checkNotNull(subscribeOnSupplier.invoke(), "subscribe is null");
    }

    @Nullable
    private Scheduler getObserveOn() {
        if (observeOnSupplier == null) {
            return null;
        }
        return Preconditions.checkNotNull(observeOnSupplier.invoke(), "observe is null");
    }

    void register(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    void unregister(@NonNull Disposable disposable) {
        compositeDisposable.remove(disposable);
    }

    @NonNull
    public <T> SingleSubscriber<T> withSingle(@NonNull Single<T> single) {
        return new SingleSubscriber<>(this, single);
    }

    @NonNull
    public <T> SingleSubscriber<T> single(@NonNull final Function1<SingleEmitter<T>, Unit> source) {

        Single<T> single = Single.create(source::invoke);

        final Scheduler subscribeOn = getSubscribeOn();
        if (subscribeOn != null) {
            single = single.subscribeOn(subscribeOn);
        }

        final Scheduler observeOn = getObserveOn();
        if (observeOn != null) {
            single = single.observeOn(observeOn);
        }

        return new SingleSubscriber<>(this, single);
    }

    @NonNull
    public <T> ObservableSubscriber<T> withObservable(@NonNull Observable<T> observable) {
        return new ObservableSubscriber<>(this, observable);
    }

    @NonNull
    public <T> ObservableSubscriber<T> observable(@NonNull final Function1<ObservableEmitter<T>, Unit> source) {

        Observable<T> observable = Observable.create(source::invoke);

        final Scheduler subscribeOn = getSubscribeOn();
        if (subscribeOn != null) {
            observable = observable.subscribeOn(subscribeOn);
        }

        final Scheduler observeOn = getObserveOn();
        if (observeOn != null) {
            observable = observable.observeOn(observeOn);
        }

        return new ObservableSubscriber<>(this, observable);
    }

    @Nonnull
    public <T> ObservableSubscriber<T> interval(long initialDelay, long period,
                                                @NonNull TimeUnit unit,
                                                @NonNull final Function1<ObservableEmitter<T>, Unit> source) {  // SUPPRESS
        Observable<Long> observable = Observable.interval(initialDelay, period, unit);

        final Scheduler subscribeOn = getSubscribeOn();
        if (subscribeOn != null) {
            observable = observable.subscribeOn(subscribeOn);
        }
        final Scheduler observeOn = getObserveOn();
        if (observeOn != null) {
            observable = observable.observeOn(observeOn);
        }

        return new ObservableSubscriber<>(this,
                observable.flatMap(ignore -> Observable.create(source::invoke)));
    }

    @NonNull
    public CompletableSubscriber withCompletable(@NonNull Completable completable) {
        return new CompletableSubscriber(this, completable);
    }

    @NonNull
    public CompletableSubscriber completable(@NonNull final Function1<CompletableEmitter, Unit> source) {

        Completable completable = Completable.create(source::invoke);

        final Scheduler subscribeOn = getSubscribeOn();
        if (subscribeOn != null) {
            completable = completable.subscribeOn(subscribeOn);
        }
        final Scheduler observeOn = getObserveOn();
        if (observeOn != null) {
            completable = completable.observeOn(observeOn);
        }

        return new CompletableSubscriber(this, completable);
    }

    public void clear() {
        compositeDisposable.clear();
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Function0<Scheduler> subscribeOnSupplier;
        private Function0<Scheduler> observeOnSupplier;

        private Builder() {
        }

        @NonNull
        public Builder withSubscribeOn(@NonNull Function0<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        @NonNull
        public Builder withObserveOn(@NonNull Function0<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        @NonNull
        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
