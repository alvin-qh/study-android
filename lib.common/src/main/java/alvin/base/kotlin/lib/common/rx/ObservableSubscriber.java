package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class ObservableSubscriber<T> extends RxSubscribe {
    private final Observable<T> observable;

    ObservableSubscriber(@NonNull final RxManager rxManager,
                         @NonNull final Observable<T> observable) {
        super(rxManager);
        this.observable = observable;
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
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext) {
        return subscribe(onNext, RxFunctions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError) {
        return subscribe(onNext, onError, RxFunctions.EMPTY_FUNCTION_0);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError,
                                      @NonNull final Function0<Unit> onComplete) {
        return subscribe(onNext, onError, onComplete, RxFunctions.EMPTY_FUNCTION_1);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError,
                                      @NonNull final Function0<Unit> onComplete,
                                      @NonNull final Function1<? super Disposable, Unit> onSubscribe) {
        final DisposableBinder binder = createDisposableBinder();

        observable.subscribe(
                onNext::invoke,
                throwable -> {
                    unbindDisposable(binder);
                    onError.invoke(throwable);
                },
                () -> {
                    unbindDisposable(binder);
                    onComplete.invoke();
                },
                d -> {
                    binder.bind(d);
                    onSubscribe.invoke(d);
                }
        );
        return binder;
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final Observer<? super T> observer) {
        final DisposableBinder binder = createDisposableBinder();

        observable.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                binder.bind(d);
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(T value) {
                observer.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                unbindDisposable(binder);
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                unbindDisposable(binder);
                observer.onComplete();
            }
        });
    }
}
