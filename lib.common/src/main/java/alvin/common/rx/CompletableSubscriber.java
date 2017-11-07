package alvin.common.rx;

import android.support.annotation.NonNull;

import alvin.common.exceptions.Throwables;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class CompletableSubscriber extends RxSubscribe {

    private final Completable completable;

    CompletableSubscriber(@NonNull RxManager rxManager,
                          @NonNull Completable completable) {
        super(rxManager);
        this.completable = completable;
    }

    @NonNull
    public Completable getCompletable() {
        return completable;
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe() {
        return registerDisposable(completable.subscribe(
                this::unregisterDisposable,
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }
        ));
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final CompletableObserver s) {
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                registerDisposable(d);
                s.onSubscribe(d);
            }

            @Override
            public void onComplete() {
                unregisterDisposable();
                s.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                unregisterDisposable();
                s.onError(e);
            }
        });
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Action onComplete,
                                      @NonNull final Consumer<? super Throwable> onError) {
        return registerDisposable(completable.subscribe(
                () -> {
                    unregisterDisposable();
                    onComplete.run();
                },
                throwable -> {
                    unregisterDisposable();
                    onError.accept(throwable);
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Action onComplete) {
        return registerDisposable(completable.subscribe(
                () -> {
                    unregisterDisposable();
                    onComplete.run();
                },
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }
        ));
    }

    @NonNull
    public final CompletableSubscriber config(@NonNull final Consumer<Completable> completableConsumer) {
        try {
            completableConsumer.accept(this.completable);
            return this;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
