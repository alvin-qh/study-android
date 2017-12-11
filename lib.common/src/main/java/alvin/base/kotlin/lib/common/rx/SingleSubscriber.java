package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

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
        return subscribe(RxFunctions.EMPTY_FUNCTION_1);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(Function1<? super T, Unit> onSuccess) {
        return subscribe(onSuccess, RxFunctions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onSuccess,
                                      @NonNull final Function1<? super Throwable, Unit> onError) {
        final DisposableBinder binder = createDisposableBinder();
        return binder.bind(
                single.subscribe(
                        result -> {
                            unbindDisposable(binder);
                            onSuccess.invoke(result);
                        },
                        throwable -> {
                            unbindDisposable(binder);
                            onError.invoke(throwable);
                        })
        );
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(final Function2<? super T, ? super Throwable, Unit> onCallback) {
        final DisposableBinder binder = createDisposableBinder();

        return binder.bind(
                single.subscribe((t, throwable) -> {
                    unbindDisposable(binder);
                    onCallback.invoke(t, throwable);
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
