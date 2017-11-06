package alvin.kotlin.common.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class RxSubscribe<T> {
    private static final Function1<Throwable, Unit> ERROR_FUNCTION = throwable -> {
        try {
            Functions.ERROR_CONSUMER.accept(throwable);
            return Unit.INSTANCE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    private static final Function0<Unit> EMPTY_FUNCTION0 = () -> Unit.INSTANCE;
    private static final Function1<Object, Unit> EMPTY_FUNCTION1 = o -> Unit.INSTANCE;

    private final RxManager manager;

    private Disposable disposable;

    RxSubscribe(@NonNull RxManager manager) {
        this.manager = manager;
    }

    public Disposable single(@Nullable final Function1<Single<T>, Unit> singleConsumer,
                             @NonNull final Function1<SingleEmitter<T>, Unit> source,
                             @NonNull final Function1<? super T, Unit> onSuccess,
                             @NonNull final Function1<? super Throwable, Unit> onError) {
        Single<T> single = Single.create(source::invoke)
                .subscribeOn(manager.getSubscribeOn())
                .observeOn(manager.getObserveOn());

        if (singleConsumer != null) {
            try {
                singleConsumer.invoke(single);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        disposable = manager.register(single.subscribe(
                t -> {
                    manager.unregister(disposable);
                    onSuccess.invoke(t);
                },
                throwable -> {
                    manager.unregister(disposable);
                    onError.invoke(throwable);
                })
        );

        return disposable;
    }

    @Nullable
    public Disposable single(@Nullable final Function1<Single<T>, Unit> singleConsumer,
                             @NonNull final Function1<SingleEmitter<T>, Unit> source,
                             @NonNull final Function1<? super T, Unit> onSuccess) {
        return single(singleConsumer, source, onSuccess, ERROR_FUNCTION);
    }

    @Nullable
    public Disposable observable(@Nullable final Function1<Observable<T>, Unit> observableConsumer,
                                 @NonNull final Function1<ObservableEmitter<T>, Unit> source,
                                 @NonNull final Function1<? super T, Unit> onNext,
                                 @NonNull final Function1<? super Throwable, Unit> onError,
                                 @NonNull final Function0<Unit> onComplete) {
        Observable<T> observable = Observable.create(source::invoke)
                .subscribeOn(manager.getSubscribeOn())
                .observeOn(manager.getObserveOn());

        if (observableConsumer != null) {
            try {
                observableConsumer.invoke(observable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        disposable = manager.register(observable.subscribe(
                onNext::invoke,
                throwable -> {
                    manager.unregister(disposable);
                    onError.invoke(throwable);
                },
                () -> {
                    manager.unregister(disposable);
                    onComplete.invoke();
                }
        ));

        return disposable;
    }

    @Nullable
    public Disposable observable(@Nullable final Function1<Observable<T>, Unit> observableConsumer,
                                 @NonNull final Function1<ObservableEmitter<T>, Unit> source,
                                 @NonNull final Function1<? super T, Unit> onNext,
                                 @NonNull final Function0<Unit> onComplete) {
        return observable(
                observableConsumer,
                source,
                onNext,
                ERROR_FUNCTION,
                onComplete);
    }

    @Nullable
    public Disposable observable(@Nullable final Function1<Observable<T>, Unit> observableConsumer,
                                 @NonNull final Function1<ObservableEmitter<T>, Unit> source,
                                 @NonNull final Function0<Unit> onComplete) {
        return observable(
                observableConsumer,
                source,
                EMPTY_FUNCTION1,
                ERROR_FUNCTION,
                onComplete);
    }

    @Nullable
    public Disposable completable(@Nullable final Function1<Completable, Unit> completableConsumer,
                                  @NonNull final Function1<CompletableEmitter, Unit> source,
                                  @NonNull final Function0<Unit> onComplete,
                                  @NonNull final Function1<Throwable, Unit> onError) {
        Completable completable = Completable.create(source::invoke)
                .subscribeOn(manager.getSubscribeOn())
                .observeOn(manager.getObserveOn());

        if (completableConsumer != null) {
            try {
                completableConsumer.invoke(completable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        disposable = manager.register(completable.subscribe(
                () -> {
                    manager.unregister(disposable);
                    onComplete.invoke();
                },
                throwable -> {
                    manager.unregister(disposable);
                    onError.invoke(throwable);
                }
        ));

        return disposable;
    }

    @Nullable
    public Disposable completable(@Nullable final Function1<Completable, Unit> completableConsumer,
                                  @NonNull final Function1<CompletableEmitter, Unit> source,
                                  @NonNull final Function0<Unit> onComplete) {
        return completable(completableConsumer, source, onComplete, ERROR_FUNCTION);
    }

    @Nullable
    public Disposable completable(@Nullable final Function1<Completable, Unit> completableConsumer,
                                  @NonNull final Function1<CompletableEmitter, Unit> source) {
        return completable(completableConsumer, source, EMPTY_FUNCTION0, ERROR_FUNCTION);
    }

    @Nullable
    public Disposable completable(@Nullable final Function1<Completable, Unit> completableConsumer,
                                  @NonNull final Function1<CompletableEmitter, Unit> source,
                                  @NonNull final Function1<Throwable, Unit> onError) {
        return completable(completableConsumer, source, EMPTY_FUNCTION0, onError);
    }

    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
