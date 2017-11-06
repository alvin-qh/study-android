package alvin.common.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class RxSubscribe<T> {
    private final RxManager manager;

    private Disposable disposable;

    RxSubscribe(@NonNull RxManager manager) {
        this.manager = manager;
    }

    public Disposable withSingle(@NonNull Single<T> single,
                                 @NonNull final Consumer<? super T> onSuccess,
                                 @NonNull final Consumer<? super Throwable> onError) {

        single.subscribeOn(manager.getSubscribeOn()).observeOn(manager.getObserveOn());

        disposable = manager.register(single.subscribe(
                t -> {
                    manager.unregister(disposable);
                    onSuccess.accept(t);
                },
                throwable -> {
                    manager.unregister(disposable);
                    onError.accept(throwable);
                })
        );

        return disposable;
    }

    public Disposable single(@Nullable final Consumer<Single<T>> singleConsumer,
                             @NonNull final SingleOnSubscribe<T> source,
                             @NonNull final Consumer<? super T> onSuccess,
                             @NonNull final Consumer<? super Throwable> onError) {

        final Single<T> single = Single.create(source);

        if (singleConsumer != null) {
            try {
                singleConsumer.accept(single);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return withSingle(single, onSuccess, onError);
    }


    @Nullable
    public Disposable single(@Nullable final Consumer<Single<T>> singleConsumer,
                             @NonNull final SingleOnSubscribe<T> source,
                             @NonNull final Consumer<? super T> onSuccess) {
        return single(singleConsumer, source, onSuccess, Functions.ERROR_CONSUMER);
    }

    @Nullable
    public Disposable withObservable(@NonNull final Observable<T> observable,
                                     @NonNull final Consumer<? super T> onNext,
                                     @NonNull final Consumer<? super Throwable> onError,
                                     @NonNull final Action onComplete) {

        observable.subscribeOn(manager.getSubscribeOn()).observeOn(manager.getObserveOn());

        disposable = manager.register(observable.subscribe(
                onNext::accept,
                throwable -> {
                    manager.unregister(disposable);
                    onError.accept(throwable);
                },
                () -> {
                    manager.unregister(disposable);
                    onComplete.run();
                }
        ));

        return disposable;
    }

    @Nullable
    public Disposable observable(@Nullable final Consumer<Observable<T>> observableConsumer,
                                 @NonNull final ObservableOnSubscribe<T> source,
                                 @NonNull final Consumer<? super T> onNext,
                                 @NonNull final Consumer<? super Throwable> onError,
                                 @NonNull final Action onComplete) {

        final Observable<T> observable = Observable.create(source);
        if (observableConsumer != null) {
            try {
                observableConsumer.accept(observable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return withObservable(observable, onNext, onError, onComplete);
    }

    @Nullable
    public Disposable observable(@Nullable final Consumer<Observable<T>> observableConsumer,
                                 @NonNull final ObservableOnSubscribe<T> source,
                                 @NonNull final Consumer<? super T> onNext,
                                 @NonNull final Action onComplete) {
        return observable(
                observableConsumer,
                source,
                onNext,
                Functions.ERROR_CONSUMER,
                onComplete);
    }

    @Nullable
    public Disposable observable(@Nullable final Consumer<Observable<T>> observableConsumer,
                                 @NonNull final ObservableOnSubscribe<T> source,
                                 @NonNull final Action onComplete) {
        return observable(
                observableConsumer,
                source,
                Functions.emptyConsumer(),
                Functions.ERROR_CONSUMER,
                onComplete);
    }

    @Nullable
    public Disposable withCompletable(@NonNull final Completable completable,
                                      @NonNull final Action onComplete,
                                      @NonNull final Consumer<Throwable> onError) {

        completable.subscribeOn(manager.getSubscribeOn()).observeOn(manager.getObserveOn());

        disposable = manager.register(completable.subscribe(
                () -> {
                    manager.unregister(disposable);
                    onComplete.run();
                },
                throwable -> {
                    manager.unregister(disposable);
                    onError.accept(throwable);
                }
        ));

        return disposable;
    }

    @Nullable
    public Disposable completable(@Nullable final Consumer<Completable> completableConsumer,
                                  @NonNull final CompletableOnSubscribe source,
                                  @NonNull final Action onComplete,
                                  @NonNull final Consumer<Throwable> onError) {
        final Completable completable = Completable.create(source);
        if (completableConsumer != null) {
            try {
                completableConsumer.accept(completable);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return withCompletable(completable, onComplete, onError);
    }

    @Nullable
    public Disposable completable(@Nullable final Consumer<Completable> completableConsumer,
                                  @NonNull final CompletableOnSubscribe source,
                                  @NonNull final Action onComplete) {
        return completable(completableConsumer, source, onComplete, Functions.ERROR_CONSUMER);
    }

    @Nullable
    public Disposable completable(@Nullable final Consumer<Completable> completableConsumer,
                                  @NonNull final CompletableOnSubscribe source) {
        return completable(completableConsumer, source, Functions.EMPTY_ACTION, Functions.ERROR_CONSUMER);
    }

    @Nullable
    public Disposable completable(@Nullable final Consumer<Completable> completableConsumer,
                                  @NonNull final CompletableOnSubscribe source,
                                  @NonNull final Consumer<Throwable> onError) {
        return completable(completableConsumer, source, Functions.EMPTY_ACTION, onError);
    }

    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
