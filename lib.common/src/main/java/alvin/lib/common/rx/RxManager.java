package alvin.lib.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
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

    void register(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    boolean unregister(@NonNull Disposable disposable) {
        return compositeDisposable.remove(disposable);
    }

    @Nonnull
    public <T> SingleSubscriber<T> with(@NonNull Single<T> single) {
        if (subscribeOnSupplier != null) {
            single = single.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            single = single.observeOn(observeOnSupplier.get());
        }
        return new SingleSubscriber<>(this, single);
    }

    @Nonnull
    public <T> ObservableSubscriber<T> with(@NonNull Observable<T> observable) {
        if (subscribeOnSupplier != null) {
            observable = observable.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            observable = observable.observeOn(observeOnSupplier.get());
        }
        return new ObservableSubscriber<>(this, observable);
    }

    @Nonnull
    public CompletableSubscriber with(@NonNull Completable completable) {
        if (subscribeOnSupplier != null) {
            completable = completable.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            completable = completable.observeOn(observeOnSupplier.get());
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
        public Builder subscribeOn(@Nonnull Supplier<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        @Nonnull
        public Builder observeOn(@Nonnull Supplier<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        @NonNull
        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
