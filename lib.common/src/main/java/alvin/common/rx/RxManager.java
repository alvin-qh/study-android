package alvin.common.rx;


import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Supplier<Scheduler> subscribeOnSupplier;
    private final Supplier<Scheduler> observeOnSupplier;

    private RxManager(Supplier<Scheduler> subscribeOnSupplier,
                      Supplier<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    public Scheduler getSubscribeOn() {
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

    public Disposable unregister(Disposable disposable) {
        compositeDisposable.remove(disposable);
        return disposable;
    }

    public <T> RxSubscribe<T> createSubscribe() {
        return new RxSubscribe<>(this);
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
