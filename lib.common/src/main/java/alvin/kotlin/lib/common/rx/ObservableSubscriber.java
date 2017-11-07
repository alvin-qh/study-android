package alvin.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class ObservableSubscriber<T> extends RxSubscribe {

    private final Observable<T> observable;

    ObservableSubscriber(@NonNull RxManager rxManager,
                         @NonNull Observable<T> observable) {
        super(rxManager);
        this.observable = observable;
    }

    @NonNull
    public Observable<T> getObservable() {
        return observable;
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe() {
        return registerDisposable(observable.subscribe(
                Functions.emptyConsumer(),
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                },
                this::unregisterDisposable
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext) {
        return registerDisposable(observable.subscribe(
                onNext::invoke,
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                },
                this::unregisterDisposable
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError) {
        return registerDisposable(observable.subscribe(
                onNext::invoke,
                throwable -> {
                    unregisterDisposable();
                    onError.invoke(throwable);
                },
                this::unregisterDisposable
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError,
                                      @NonNull final Function0<Unit> onComplete) {
        return registerDisposable(observable.subscribe(
                onNext::invoke,
                throwable -> {
                    unregisterDisposable();
                    onError.invoke(throwable);
                },
                () -> {
                    unregisterDisposable();
                    onComplete.invoke();
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super T, Unit> onNext,
                                      @NonNull final Function1<? super Throwable, Unit> onError,
                                      @NonNull final Function0<Unit> onComplete,
                                      @NonNull final Function1<? super Disposable, Unit> onSubscribe) {
        return observable.subscribe(
                onNext::invoke,
                throwable -> {
                    unregisterDisposable();
                    onError.invoke(throwable);
                },
                () -> {
                    unregisterDisposable();
                    onComplete.invoke();
                },
                disposable -> {
                    registerDisposable(disposable);
                    onSubscribe.invoke(disposable);
                }
        );
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final Observer<? super T> observer) {
        observable.subscribe(new Observer<T>() {

            @Override
            public void onSubscribe(Disposable d) {
                registerDisposable(d);
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(T value) {
                observer.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                unregisterDisposable();
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                unregisterDisposable();
                observer.onComplete();
            }
        });
    }

    @NonNull
    public final ObservableSubscriber<T> config(@NonNull final Function1<Observable<T>, Unit> observableFn) {
        observableFn.invoke(this.observable);
        return this;
    }
}
