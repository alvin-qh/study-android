package alvin.common.rx;

import android.support.annotation.NonNull;

import alvin.common.exceptions.Throwables;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

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

    @NonNull
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
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext) {
        return registerDisposable(observable.subscribe(
                onNext,
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                },
                this::unregisterDisposable
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError) {
        return registerDisposable(observable.subscribe(
                onNext,
                throwable -> {
                    unregisterDisposable();
                    onError.accept(throwable);
                },
                this::unregisterDisposable
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError,
                                      @NonNull final Action onComplete) {
        return registerDisposable(observable.subscribe(
                onNext,
                throwable -> {
                    unregisterDisposable();
                    onError.accept(throwable);
                },
                () -> {
                    unregisterDisposable();
                    onComplete.run();
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super T> onNext,
                                      @NonNull final Consumer<? super Throwable> onError,
                                      @NonNull final Action onComplete,
                                      @NonNull final Consumer<? super Disposable> onSubscribe) {
        return observable.subscribe(
                onNext,
                throwable -> {
                    unregisterDisposable();
                    onError.accept(throwable);
                },
                () -> {
                    unregisterDisposable();
                    onComplete.run();
                },
                disposable -> {
                    registerDisposable(disposable);
                    onSubscribe.accept(disposable);
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
    public final ObservableSubscriber<T> config(@NonNull final Consumer<Observable<T>> observableConsumer) {
        try {
            observableConsumer.accept(this.observable);
            return this;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
