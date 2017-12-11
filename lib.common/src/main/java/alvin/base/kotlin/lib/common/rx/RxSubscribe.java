package alvin.base.kotlin.lib.common.rx;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

abstract class RxSubscribe {
    private final RxManager rxManager;

    RxSubscribe(@NonNull RxManager rxManager) {
        this.rxManager = rxManager;
    }

    @NonNull
    DisposableBinder createDisposableBinder() {
        final DisposableBinder binder = new DisposableBinder();
        rxManager.register(binder);
        return binder;
    }

    void unbindDisposable(@NotNull final Disposable disposable) {
        if (!rxManager.unregister(disposable)) {
            disposable.dispose();
        }
    }
}
