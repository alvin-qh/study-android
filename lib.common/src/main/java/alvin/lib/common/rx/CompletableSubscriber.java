package alvin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

public class CompletableSubscriber extends RxSubscribe {

    private final Completable completable;

    CompletableSubscriber(@NonNull final RxManager rxManager,
                          @NonNull final Completable completable) {
        super(rxManager);
        this.completable = completable;
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe() {
        return subscribe(Functions.EMPTY_ACTION);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Action onComplete) {
        return subscribe(onComplete, Functions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Consumer<? super Throwable> onError) {
        return subscribe(Functions.EMPTY_ACTION, onError);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Action onComplete,
                                      @NonNull final Consumer<? super Throwable> onError) {
        final DisposableBinder binder = createDisposableBinder();

        return binder.bind(completable.subscribe(
                () -> {
                    unbindDisposable(binder);
                    onComplete.run();
                },
                throwable -> {
                    unbindDisposable(binder);
                    onError.accept(throwable);
                }
        ));
    }

    @SchedulerSupport(SchedulerSupport.NONE)
    public final void subscribe(@NonNull final CompletableObserver s) {
        final DisposableBinder binder = createDisposableBinder();

        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                binder.bind(d);
                s.onSubscribe(d);
            }

            @Override
            public void onComplete() {
                unbindDisposable(binder);
                s.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                unbindDisposable(binder);
                s.onError(e);
            }
        });
    }
}
