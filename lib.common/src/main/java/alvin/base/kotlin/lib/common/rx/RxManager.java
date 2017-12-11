package alvin.base.kotlin.lib.common.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.functions.Function0;

public final class RxManager {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final Function0<Scheduler> subscribeOnSupplier;
    private final Function0<Scheduler> observeOnSupplier;

    private RxManager(@Nullable final Function0<Scheduler> subscribeOnSupplier,
                      @Nullable final Function0<Scheduler> observeOnSupplier) {
        this.subscribeOnSupplier = subscribeOnSupplier;
        this.observeOnSupplier = observeOnSupplier;
    }

    boolean register(@NonNull final Disposable disposable) {
        return compositeDisposable.add(disposable);
    }

    boolean unregister(@NonNull final Disposable disposable) {
        return compositeDisposable.remove(disposable);
    }

    @NonNull
    public <T> SingleSubscriber<T> with(@NonNull Single<T> single) {
        if (subscribeOnSupplier != null) {
            single = single.subscribeOn(subscribeOnSupplier.invoke());
        }
        if (observeOnSupplier != null) {
            single = single.observeOn(observeOnSupplier.invoke());
        }
        return new SingleSubscriber<>(this, single);
    }

    @NonNull
    public <T> ObservableSubscriber<T> with(@NonNull Observable<T> observable) {
        if (subscribeOnSupplier != null) {
            observable = observable.subscribeOn(subscribeOnSupplier.invoke());
        }
        if (observeOnSupplier != null) {
            observable = observable.observeOn(observeOnSupplier.invoke());
        }
        return new ObservableSubscriber<>(this, observable);
    }

    @NonNull
    public CompletableSubscriber with(@NonNull Completable completable) {
        if (subscribeOnSupplier != null) {
            completable = completable.subscribeOn(subscribeOnSupplier.invoke());
        }
        if (observeOnSupplier != null) {
            completable = completable.observeOn(observeOnSupplier.invoke());
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
        public Builder subscribeOn(@NonNull Function0<Scheduler> subscribeOnSupplier) {
            this.subscribeOnSupplier = subscribeOnSupplier;
            return this;
        }

        @NonNull
        public Builder observeOn(@NonNull Function0<Scheduler> observeOnSupplier) {
            this.observeOnSupplier = observeOnSupplier;
            return this;
        }

        @NonNull
        public RxManager build() {
            return new RxManager(subscribeOnSupplier, observeOnSupplier);
        }
    }
}
