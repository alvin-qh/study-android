package alvin.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class SingleSubscriber<T> extends RxSubscribe {

    private final Single<T> single;

    SingleSubscriber(@NonNull RxManager rxManager,
                     @NonNull Single<T> single) {
        super(rxManager);
        this.single = single;
    }

    @NonNull
    public Single<T> single() {
        return single;
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe() {
        return registerDisposable(single.subscribe(
                value -> unregisterDisposable(),
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(final BiConsumer<? super T, ? super Throwable> onCallback) {
        return registerDisposable(single.subscribe(
                (value, throwable) -> {
                    unregisterDisposable();
                    onCallback.accept(value, throwable);
                }));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(Consumer<? super T> onSuccess) {
        return registerDisposable(single.subscribe(
                value -> {
                    unregisterDisposable();
                    onSuccess.accept(value);
                },
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onSuccess,
                                      @NonNull final Consumer<? super Throwable> onError) {
        return registerDisposable(single.subscribe(
                result -> {
                    unregisterDisposable();
                    onSuccess.accept(result);
                },
                throwable -> {
                    unregisterDisposable();
                    onError.accept(throwable);
                })
        );
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final SingleObserver<? super T> subscriber) {
        single.subscribe(new SingleObserver<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                registerDisposable(d);
                subscriber.onSubscribe(d);
            }

            @Override
            public void onSuccess(T value) {
                unregisterDisposable();
                subscriber.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                unregisterDisposable();
                subscriber.onError(e);
            }
        });
    }
}
