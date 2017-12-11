package alvin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class SingleSubscriber<T> extends RxSubscribe {
    private final Single<T> single;

    SingleSubscriber(@NonNull final RxManager rxManager,
                     @NonNull final Single<T> single) {
        super(rxManager);
        this.single = single;
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer());
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(final BiConsumer<? super T, ? super Throwable> onCallback) {
        final DisposableBinder binder = createDisposableBinder();

        return binder.bind(
                single.subscribe((value, throwable) -> {
                    unbindDisposable(binder);
                    onCallback.accept(value, throwable);
                })
        );
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(Consumer<? super T> onSuccess) {
        return subscribe(onSuccess, Functions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onSuccess,
                                      @NonNull final Consumer<? super Throwable> onError) {
        final DisposableBinder binder = createDisposableBinder();

        return binder.bind(
                single.subscribe(
                        result -> {
                            unbindDisposable(binder);
                            onSuccess.accept(result);
                        },
                        throwable -> {
                            unbindDisposable(binder);
                            onError.accept(throwable);
                        })
        );
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final SingleObserver<? super T> subscriber) {
        final DisposableBinder binder = createDisposableBinder();

        single.subscribe(new SingleObserver<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                binder.bind(d);
                subscriber.onSubscribe(d);
            }

            @Override
            public void onSuccess(T value) {
                unbindDisposable(binder);
                subscriber.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                unbindDisposable(binder);
                subscriber.onError(e);
            }
        });
    }
}
