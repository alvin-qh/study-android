package alvin.kotlin.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

public class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Function0<Scheduler> subscribeOnSupplier;
    private final Function0<Scheduler> observeOnSupplier;

    private RxManager(Function0<Scheduler> subscribeOnSupplier,
                      Function0<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    Scheduler getSubscribeOn() {
        if (subscribeOnSupplier == null) {
            return null;
        }
        return subscribeOnSupplier.invoke();
    }

    Scheduler getObserveOn() {
        if (observeOnSupplier == null) {
            return null;
        }
        return observeOnSupplier.invoke();
    }

    Disposable register(Disposable disposable) {
        compositeDisposable.add(disposable);
        return disposable;
    }

    Disposable unregister(Disposable disposable) {
        compositeDisposable.remove(disposable);
        return disposable;
    }

    public <T> SingleSubscriber<T> withSingle(Single<T> single) {
        return new SingleSubscriber<>(this, single);
    }

    public <T> SingleSubscriber<T> single(
            @Nullable final Function1<Single<T>, Unit> singleFn,
            @NonNull final Function1<SingleEmitter<T>, Unit> source) {

        final Single<T> single = Single.create(source::invoke)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (singleFn != null) {
                singleFn.invoke(single);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new SingleSubscriber<>(this, single);
    }

    public <T> ObservableSubscriber<T> withObservable(Observable<T> observable) {
        return new ObservableSubscriber<>(this, observable);
    }

    public <T> ObservableSubscriber<T> observable(
            @Nullable final Function1<Observable<T>, Unit> observableFn,
            @NonNull final Function1<ObservableEmitter<T>, Unit> source) {

        final Observable<T> observable = Observable.create(source::invoke)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (observableFn != null) {
                observableFn.invoke(observable);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ObservableSubscriber<>(this, observable);
    }

    public CompletableSubscriber withCompletable(Completable completable) {
        return new CompletableSubscriber(this, completable);
    }

    public CompletableSubscriber completable(
            @Nullable final Function1<Completable, Unit> completableFn,
            @NonNull final Function1<CompletableEmitter, Unit> source) {

        final Completable completable = Completable.create(source::invoke)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (completableFn != null) {
                completableFn.invoke(completable);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CompletableSubscriber(this, completable);
    }

    public void clear() {
        compositeDisposable.clear();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Function0<Scheduler> subscribeOnSupplier;
        private Function0<Scheduler> observeOnSupplier;

        private Builder() {
        }

        @Nonnull
        public Builder withSubscribeOn(@Nonnull Function0<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        @Nonnull
        public Builder withObserveOn(@Nonnull Function0<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
