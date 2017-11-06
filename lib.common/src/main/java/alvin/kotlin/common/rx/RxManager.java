package alvin.kotlin.common.rx;


import javax.annotation.Nonnull;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.functions.Function0;

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
