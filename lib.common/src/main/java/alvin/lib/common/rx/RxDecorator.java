package alvin.lib.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public final class RxDecorator {
    private final Supplier<Scheduler> subscribeOnSupplier;
    private final Supplier<Scheduler> observeOnSupplier;

    private RxDecorator(@Nullable Supplier<Scheduler> subscribeOnSupplier,
                        @Nullable Supplier<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    @Nonnull
    public <T> Single<T> de(@NonNull Single<T> single) {
        if (subscribeOnSupplier != null) {
            single = single.subscribeOn(subscribeOnSupplier.get());
        }
        if (observeOnSupplier != null) {
            single = single.observeOn(observeOnSupplier.get());
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
        return completable;
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
        public RxDecorator build() {
            return new RxDecorator(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
