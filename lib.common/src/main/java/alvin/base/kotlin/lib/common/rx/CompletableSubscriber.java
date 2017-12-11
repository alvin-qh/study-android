package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

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
        return subscribe(RxFunctions.EMPTY_FUNCTION_0);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function0<Unit> onComplete) {
        return subscribe(onComplete, RxFunctions.ON_ERROR_MISSING);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function1<? super Throwable, Unit> onError) {
        return subscribe(RxFunctions.EMPTY_FUNCTION_0, onError);
    }

    @NonNull
    @CheckReturnValue
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function0<Unit> onComplete,
                                      @NonNull final Function1<? super Throwable, Unit> onError) {
        final DisposableBinder binder = createDisposableBinder();

        return binder.bind(
                completable.subscribe(
                        () -> {
                            unbindDisposable(binder);
                            onComplete.invoke();
                        },
                        throwable -> {
                            unbindDisposable(binder);
                            onError.invoke(throwable);
                        }
                )
        );
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
