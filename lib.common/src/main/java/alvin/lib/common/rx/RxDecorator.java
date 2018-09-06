package alvin.lib.common.rx;


import android.support.annotation.NonNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public final class RxDecorator {
    private final Supplier<Scheduler> subscribeOnSupplier;
    private final Supplier<Scheduler> observeOnSupplier;
    private final Integer retryTimes;

    private RxDecorator(Supplier<Scheduler> subscribeOnSupplier,
                        Supplier<Scheduler> observeOnSupplier,
                        Integer retryTimes) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
        this.retryTimes = retryTimes;
    }

    @Nonnull
    public <T> Single<T> de(@NonNull Single<T> single) {
        if (subscribeOnSupplier != null) {
            single = single.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            single = single.observeOn(observeOnSupplier.get());
        }
        if (retryTimes != null) {
            single = single.retry(retryTimes);
        }
        return single;
    }

    @Nonnull
    public <T> Observable<T> de(@NonNull Observable<T> observable) {
        if (subscribeOnSupplier != null) {
            observable = observable.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            observable = observable.observeOn(observeOnSupplier.get());
        }
        if (retryTimes != null) {
            observable = observable.retry(retryTimes);
        }
        return observable;
    }

    @Nonnull
    public Completable de(@NonNull Completable completable) {
        if (subscribeOnSupplier != null) {
            completable = completable.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            completable = completable.observeOn(observeOnSupplier.get());
        }
        if (retryTimes != null) {
            completable = completable.retry(retryTimes);
        }
        return completable;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Supplier<Scheduler> subscribeOnSupplier;
        private Supplier<Scheduler> observeOnSupplier;
        private Integer retryTimes = null;

        private Builder() {
        }

        public Builder subscribeOn(@Nonnull Supplier<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        public Builder observeOn(@Nonnull Supplier<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        public Builder retryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public RxDecorator build() {
            return new RxDecorator(
                    subscribeOnSupplier,
                    observeOnSupplier,
                    retryTimes);
        }
    }
}
