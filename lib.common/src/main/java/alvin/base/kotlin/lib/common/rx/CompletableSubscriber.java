package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class CompletableSubscriber extends RxSubscribe {

    private Completable completable;

    CompletableSubscriber(@NonNull RxManager rxManager,
                          @NonNull Completable completable) {
        super(rxManager);
        this.completable = completable;
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
    public final Disposable subscribe(@NonNull final Function0<Unit> onComplete,
                                      @NonNull final Function1<Throwable, Unit> onError) {
        return registerDisposable(completable.subscribe(
                () -> {
                    unregisterDisposable();
                    onComplete.invoke();
                },
                throwable -> {
                    unregisterDisposable();
                    onError.invoke(throwable);
                }
        ));
    }

    @NonNull
    @SchedulerSupport(SchedulerSupport.NONE)
    public final Disposable subscribe(@NonNull final Function0<Unit> onComplete) {
        return registerDisposable(completable.subscribe(
                () -> {
                    unregisterDisposable();
                    onComplete.invoke();
                },
                throwable -> {
                    unregisterDisposable();
                    Functions.ERROR_CONSUMER.accept(throwable);
                }
        ));
    }

    @NonNull
    public final CompletableSubscriber config(@NonNull final Function1<Completable, Completable> configFn) {
        this.completable = configFn.invoke(this.completable);
        return this;
    }
}
