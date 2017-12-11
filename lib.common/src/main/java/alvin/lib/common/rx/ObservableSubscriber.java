package alvin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

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
        return subscribe(Functions.emptyConsumer());
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext) {
        return subscribe(onNext, Functions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError) {
        return subscribe(onNext, onError, Functions.EMPTY_ACTION);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError,
                                      @NonNull final Action onComplete) {
        return subscribe(onNext, onError, onComplete, Functions.emptyConsumer());
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError,
                                      @NonNull final Action onComplete,
                                      @NonNull final Consumer<? super Disposable> onSubscribe) {
        final DisposableBinder binder = createDisposableBinder();

        observable.subscribe(
                onNext,
                throwable -> {
                    unbindDisposable(binder);
                    onError.accept(throwable);
                },
                () -> {
                    unbindDisposable(binder);
                    onComplete.run();
                },
                disposable -> {
                    binder.bind(disposable);
                    onSubscribe.accept(disposable);
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
