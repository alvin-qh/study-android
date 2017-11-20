package alvin.lib.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public final class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Supplier<Scheduler> subscribeOnSupplier;
    private final Supplier<Scheduler> observeOnSupplier;

    private RxManager(@Nullable Supplier<Scheduler> subscribeOnSupplier,
                      @Nullable Supplier<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    @Nullable
    private Scheduler getSubscribeOn() {
        if (subscribeOnSupplier == null) {
            return null;
        }
        return subscribeOnSupplier.get();
    }

    @Nullable
    private Scheduler getObserveOn() {
        if (observeOnSupplier == null) {
            return null;
        }
        return observeOnSupplier.get();
    }

    void register(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    void unregister(@NonNull Disposable disposable) {
        compositeDisposable.remove(disposable);
    }

    @Nonnull
    public <T> SingleSubscriber<T> withSingle(@NonNull final Single<T> single) {
        return new SingleSubscriber<>(this, single);
    }

    @Nonnull
    public <T> SingleSubscriber<T> single(@NonNull final SingleOnSubscribe<T> source) {

        Single<T> single = Single.create(source);

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

    @Nonnull
    public <T> ObservableSubscriber<T> withObservable(@NonNull final Observable<T> observable) {
        return new ObservableSubscriber<>(this, observable);
    }

    @Nonnull
    public <T> ObservableSubscriber<T> observable(@NonNull final ObservableOnSubscribe<T> source) {
        Observable<T> observable = Observable.create(source);

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
                                                @NonNull final ObservableOnSubscribe<T> source) {
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
                observable.flatMap(ignore -> Observable.create(source)));
    }

    @Nonnull
    public CompletableSubscriber withCompletable(@NonNull final Completable completable) {
        return new CompletableSubscriber(this, completable);
    }

    @Nonnull
    public CompletableSubscriber completable(@NonNull final CompletableOnSubscribe source) {
        Completable completable = Completable.create(source);

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

    @Nonnull
    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Supplier<Scheduler> subscribeOnSupplier;
        private Supplier<Scheduler> observeOnSupplier;

        private Builder() {
        }

        @Nonnull
        public Builder withSubscribeOn(@Nonnull Supplier<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        @Nonnull
        public Builder withObserveOn(@Nonnull Supplier<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        @NonNull
        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
