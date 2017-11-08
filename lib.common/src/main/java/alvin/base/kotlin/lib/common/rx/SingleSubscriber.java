package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class SingleSubscriber<T> extends RxSubscribe {

    private Single<T> single;

    SingleSubscriber(@NonNull RxManager rxManager,
                     @NonNull Single<T> single) {
        super(rxManager);
        this.single = single;
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
    public final Disposable subscribe(final Function2<? super T, ? super Throwable, Unit> onCallback) {
        return registerDisposable(single.subscribe(
                (value, throwable) -> {
                    unregisterDisposable();
                    onCallback.invoke(value, throwable);
                }));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(Function1<? super T, Unit> onSuccess) {
        return registerDisposable(single.subscribe(
                value -> {
                    unregisterDisposable();
                    onSuccess.invoke(value);
                },
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onSuccess,
                                      @NonNull final Function1<? super Throwable, Unit> onError) {
        return registerDisposable(single.subscribe(
                result -> {
                    unregisterDisposable();
                    onSuccess.invoke(result);
                },
                throwable -> {
                    unregisterDisposable();
                    onError.invoke(throwable);
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

    @NotNull
    public final SingleSubscriber<T> config(@NonNull final Function1<Single<T>, Single<T>> singleFn) {
        this.single = singleFn.invoke(this.single);
        return this;
    }
}
