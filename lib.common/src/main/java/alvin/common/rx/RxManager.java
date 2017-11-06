package alvin.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
import io.reactivex.functions.Consumer;

public class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Supplier<Scheduler> subscribeOnSupplier;
    private final Supplier<Scheduler> observeOnSupplier;

    private RxManager(Supplier<Scheduler> subscribeOnSupplier,
                      Supplier<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    Scheduler getSubscribeOn() {
        if (subscribeOnSupplier == null) {
            return null;
        }
        return subscribeOnSupplier.get();
    }

    Scheduler getObserveOn() {
        if (observeOnSupplier == null) {
            return null;
        }
        return observeOnSupplier.get();
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

    public <T> SingleSubscriber<T> single(@Nullable final Consumer<Single<T>> singleConsumer,
                                          @NonNull final SingleOnSubscribe<T> source) {
        final Single<T> single = Single.create(source)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (singleConsumer != null) {
                singleConsumer.accept(single);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new SingleSubscriber<>(this, single);
    }

    public <T> ObservableSubscriber<T> withObservable(Observable<T> observable) {
        return new ObservableSubscriber<>(this, observable);
    }

    public <T> ObservableSubscriber<T> observable(@Nullable final Consumer<Observable<T>> observableConsumer,
                                                  @NonNull final ObservableOnSubscribe<T> source) {
        final Observable<T> observable = Observable.create(source)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (observableConsumer != null) {
                observableConsumer.accept(observable);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ObservableSubscriber<>(this, observable);
    }

    public CompletableSubscriber withCompletable(Completable completable) {
        return new CompletableSubscriber(this, completable);
    }

    public CompletableSubscriber completable(@Nullable final Consumer<Completable> completableConsumer,
                                             @NonNull final CompletableOnSubscribe source) {
        final Completable completable = Completable.create(source)
                .subscribeOn(getSubscribeOn()).observeOn(getObserveOn());

        try {
            if (completableConsumer != null) {
                completableConsumer.accept(completable);
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

        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
